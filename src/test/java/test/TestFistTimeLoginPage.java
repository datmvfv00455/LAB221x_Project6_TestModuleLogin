package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.TestConfig;
import config.TestUtils;
import lms.lab221x.ass6.dao.UserDao;
import lms.lab221x.ass6.model.FistTimeLoginModel;
import lms.lab221x.ass6.model.QuestionModel;
import lms.lab221x.ass6.service.UserService;
import lms.lab221x.ass6.utils.JsonResponse;

@WebMvcTest()
@SpringJUnitConfig(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestFistTimeLoginPage {

	/** The mock mvc. */
	@Autowired
	MockMvc mockMvc;

	/** The mapper. */
	@Autowired
	ObjectMapper mapper;

	/** The user service. */
	@Autowired
	UserService userService;

	/** The user dao. */
	@Autowired
	UserDao userDao;

	@BeforeEach
	void beforeEach(TestInfo testInfo) throws Exception {
		TestUtils.printlnTestInfo(testInfo);
		TestUtils.createBackupFile(userDao.getUserDataFile());
	}

	@AfterEach
	void afterEach(TestInfo testInfo) throws IOException {
		TestUtils.printlnTestInfo(testInfo);
		TestUtils.restoreBackupFile();
	}

	@ParameterizedTest(name = ""
			+ "#{index} => "
			+ "TestCase : {0} ["
			+ "ID: {1} - "
			+ "Password: {2} - "
			+ "QuestionIDs:{3} - "
			+ "OldPassword:{4} - "
			+ "NewPassword:{5} - "
			+ "ConfirmPassword:{6}]")
	@CsvFileSource(resources = "/TestFistTimeLoginPage.csv", numLinesToSkip = 1, delimiter = ';')
	void testMockMvcFistTimeLoginPage_LoadCsvFile_ExpectJsonReturnEqual(
			String testCaseName,
			String userID,
			String userPassword,
			String answeredQuestionIDs,
			String oldPassword,
			String newPassword,
			String confirmPassword,
			String expectJsonValue) throws Exception {

		MockHttpServletRequestBuilder loginPostRequest;
		MockHttpServletRequestBuilder fistTimePostRequest;
		MockHttpSession               session;
		JsonResponse                  expectJson;
		JsonResponse                  responseJson;

		// Step 01. Get Login Post Request
		loginPostRequest = TestUtils.getLoginPostRequest(userID,
				userPassword);

		// Step 02. Login User And Get Session Back
		session = loginUserAndGetSession(loginPostRequest);

		// Step 03. Get Expect_Json Object
		// By Mapping EXPECT_JSON Column In Csv File
		expectJson = mapper.readValue(expectJsonValue,
				JsonResponse.class);

		// Step 04. Get FistTimeLogin PostRequest With Previous Session
		fistTimePostRequest = this.getFistTimeLoginPostRequest(
				userID,
				userPassword,
				answeredQuestionIDs,
				oldPassword,
				newPassword,
				confirmPassword,
				session);

		// Step 04. MockMvc And Get Response_Json Object Back
		responseJson = TestUtils.getMappingJsonResponse(
				mockMvc,
				fistTimePostRequest);

		// Step 05. Println Expect_Json Object
		// And Response_Json Object To Console
		TestUtils.printlnJson(expectJson, responseJson);

		// Step 06. Assert Expect_Json Object
		// And Response_Json Object Are Equal
		assertEquals(expectJson, responseJson);

	}

	private MockHttpServletRequestBuilder getFistTimeLoginPostRequest(
			String userID,
			String userPassword,
			String answeredQuestionIDs,
			String oldPassword,
			String newPassword,
			String confirmPassword,
			MockHttpSession session)
			throws JsonProcessingException {

		List<QuestionModel>           answeredQuestions;
		FistTimeLoginModel            fistTimeLoginModel;
		String                        fistTimeLoginRequestJson;
		MockHttpServletRequestBuilder fistTimeLoginPostRequest;

		// Get Answered Question List
		answeredQuestions = getAnsweredQuestions(answeredQuestionIDs);

		// Build FistTimeLogin Object
		fistTimeLoginModel = FistTimeLoginModel.builder()
				.questionList(answeredQuestions)
				.oldPassword(oldPassword)
				.newPassword(newPassword)
				.confirmPassword(confirmPassword)
				.build();

		// Covert FistTimeLogin Object To Json
		fistTimeLoginRequestJson = mapper.writer()
				.writeValueAsString(fistTimeLoginModel);

		// Build FistTimeLogin PostRequest With Session
		fistTimeLoginPostRequest = MockMvcRequestBuilders
				.post("/fisttime-login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(fistTimeLoginRequestJson)
				.accept(MediaType.APPLICATION_JSON)
				.session(session);

		return fistTimeLoginPostRequest;
	}

	private MockHttpSession loginUserAndGetSession(
			MockHttpServletRequestBuilder loginRequest)
			throws Exception {

		return (MockHttpSession) mockMvc
				.perform(loginRequest)
				.andExpect(
					status()
						.isOk())
				.andExpect(
					jsonPath("$.redirectUrl")
						.value("fisttime-login"))
				.andReturn()
				.getRequest()
				.getSession();

	}

	private List<QuestionModel> getAnsweredQuestions(
			String answeredQuestionIDs) {
		
		List<QuestionModel> answeredQuestions;
		String[]            questionIdArr;
		List<QuestionModel> sampleQuestionList;

		answeredQuestions = new ArrayList<>();

		if (answeredQuestionIDs == null) {
			return answeredQuestions;
		}

		questionIdArr = answeredQuestionIDs
				.replace("]", "").replace("[", "")
				.split(",");

		sampleQuestionList = TestUtils.getSampleQuestions();

		for (String questionId : questionIdArr) {
			for (QuestionModel question : sampleQuestionList) {
				if (question.getId().equals(questionId)) {
					answeredQuestions.add(question);
				}
			}
		}
		return answeredQuestions;
	}
}
