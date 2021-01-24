package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.TestConfig;
import config.TestUtils;
import lms.lab221x.ass6.controller.LoginController;
import lms.lab221x.ass6.dao.UserDao;
import lms.lab221x.ass6.service.UserService;
import lms.lab221x.ass6.utils.JsonResponse;

@WebMvcTest(LoginController.class)
@SpringJUnitConfig(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestLoginPage {

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
	}

	@AfterEach
	void afterEach(TestInfo testInfo) throws IOException {
		TestUtils.printlnTestInfo(testInfo);	
	}
	@BeforeAll
	void beforeAll() throws IOException{
		TestUtils.createBackupFile(userDao.getUserDataFile());
	}

	@AfterAll
	public void afterAll() throws IOException {	
		TestUtils.restoreBackupFile();
	}

	@ParameterizedTest(
			name = "#{index} => "
			+ "TestCase : {0} ["
			+ "ID: {1} - "
			+ "Password: {2}]")
	@CsvFileSource(resources = "/TestLoginPageData.csv",
			numLinesToSkip = 1, 
			delimiter = ';')
	void testMockMvcLoginPage_LoadCsvFile_ExpectJsonReturnEqual(
			String testCaseName,
			String userID,
			String userPassword,
			String expectJsonValue) throws Exception {

		MockHttpServletRequestBuilder loginPostRequest;
		JsonResponse                  expectJson;
		JsonResponse                  responseJson;

		// Step 01. Get Login Post Request
		loginPostRequest = TestUtils
				.getLoginPostRequest(userID, userPassword);

		// Step 02. Get Expect_Json Object 
		// By Mapping EXPECT_JSON Column In Csv File
		expectJson = mapper.readValue(
				expectJsonValue,
				JsonResponse.class);

		// Step 03. MockMvc And Get Response_Json Object Back
		responseJson = TestUtils
				.getMappingJsonResponse(
						mockMvc,
						loginPostRequest);

		// Step 04. Println Expect_Json Object 
		// And Response_Json Object To Console
	 	TestUtils.printlnJson(expectJson, responseJson);

		// Step 05. Assert Expect_Json Object 
		// And Response_Json Object Are Equal
		assertEquals(expectJson, responseJson);
	}

}
