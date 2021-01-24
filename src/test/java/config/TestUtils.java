package config;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.TestInfo;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lms.lab221x.ass6.model.QuestionModel;
import lms.lab221x.ass6.model.UserModel;
import lms.lab221x.ass6.utils.JsonResponse;

public class TestUtils {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	/** The question list. */
	static List<QuestionModel> questionTempList = new ArrayList<>();

	/**
	 * Gets the login post request.
	 *
	 * @param userID
	 *     the user ID
	 * @param userPassword
	 *     the user password
	 * 
	 * @return the login post request
	 * 
	 * @throws JsonProcessingException
	 *     the json processing exception
	 */
	public static MockHttpServletRequestBuilder getLoginPostRequest(
		String userID, String userPassword)
		throws JsonProcessingException {

		UserModel                     requestUser;
		String                        requestJson;
		MockHttpServletRequestBuilder postRequest;

		requestUser = UserModel.builder()
			.id(userID)
			.password(userPassword)
			.build();

		requestJson = mapper.writer()
			.writeValueAsString(requestUser);

		postRequest = MockMvcRequestBuilders
			.post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson)
			.accept(MediaType.APPLICATION_JSON);

		return postRequest;

	}

	/**
	 * Gets the sample questions.
	 *
	 * @return the sample questions
	 */
	public static List<QuestionModel> getSampleQuestions() {

		if (questionTempList.isEmpty()) {
			QuestionModel q01 = QuestionModel.builder()
				.id("q01")
				.label("Question01")
				.answer("Answered Question01")
				.build();

			QuestionModel q02 = QuestionModel.builder()
				.id("q02")
				.label("Question02")
				.answer("Answered Question02")
				.build();

			QuestionModel q03 = QuestionModel.builder()
				.id("q03")
				.label("Question03")
				.answer("Answered Question03")
				.build();

			QuestionModel q04 = QuestionModel.builder()
				.id("q04")
				.label("Question04")
				.answer("Answered Question04")
				.build();

			QuestionModel q05 = QuestionModel.builder()
				.id("q05")
				.label("Question05")
				.answer("Answered Question05")
				.build();

			questionTempList.add(q01);
			questionTempList.add(q02);
			questionTempList.add(q03);
			questionTempList.add(q04);
			questionTempList.add(q05);
		}

		return questionTempList;

	}

	/**
	 * Colum empty or null.
	 *
	 * @param columContent
	 *     the s
	 * 
	 * @return the string
	 */
	public static String columEmptyOrNull(String columContent) {

		if (columContent == null) {
			return "";
		}
		return columContent.equals("null") ? null : columContent;

	}

	/**
	 * Println test info.
	 *
	 * @param testInfo
	 *     the test info
	 */
	public static void printlnTestInfo(TestInfo testInfo) {
		System.out.format("\n%s %-120s %s\n\n",
			"====> ", testInfo.getDisplayName(), " <====");
	}

	/**
	 * Println json.
	 *
	 * @param expectJson
	 *     the expect json
	 * @param responseJson
	 *     the response json
	 * 
	 * @throws JsonProcessingException
	 *     the json processing exception
	 */	
	public static void printlnJson(
		JsonResponse expectJson,
		JsonResponse responseJson)
		throws JsonProcessingException {

		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		String expectJsonStr   = mapper
			.writeValueAsString(expectJson);
		String responseJsonStr = mapper
			.writeValueAsString(responseJson);

		String[] expectJsonArr   = expectJsonStr
			.split("\r\n");
		String[] responseJsonArr = responseJsonStr
			.split("\r\n");

		System.out.format("\n%-110s %-110s\n",
			"Expect Json", "Response Json");

		int jsonArrLength = responseJsonArr.length;
		for (int i = 0; i < jsonArrLength; i++) {
			System.out.format("%-110s %-110s\n",
				expectJsonArr[i],
				responseJsonArr[i]);
		}

	}

	/**
	 * Gets the mapping json response.
	 *
	 * @param mockMvc
	 *     the mock mvc
	 * @param postRequest
	 *     the post request
	 * 
	 * @return the mapping json response
	 * 
	 * @throws Exception
	 *     the exception
	 */
	public static JsonResponse getMappingJsonResponse(MockMvc mockMvc,
		MockHttpServletRequestBuilder postRequest)
		throws Exception {

		MvcResult mvcResult = mockMvc
			.perform(postRequest)
			.andExpect(status().isOk())
			.andExpect(content()
				.contentType(
					MediaType.APPLICATION_JSON))
			.andReturn();

		String jsonResponse = mvcResult
			.getResponse()
			.getContentAsString();	 

		JsonResponse mappingJsonResponse = mapper
			.readValue(jsonResponse, JsonResponse.class);

		return mappingJsonResponse;

	}

	/** The user data file. */
	static File userDataFile;

	/** The user data backup file. */
	static File userDataBackupFile;

	/**
	 * Creates the backup file.
	 *
	 * @param resourcefile
	 *     the resourcefile
	 * 
	 * @throws IOException
	 *     Signals that an I/O exception has occurred.
	 */
	public static void createBackupFile(
		File resourcefile) throws IOException {

		if (userDataFile == null) {
			userDataFile = resourcefile;
		}

		if (userDataBackupFile == null) {
			String userDataBackupFilePath = resourcefile.getParent()
				+ "\\UserDataBackup.csv";

			userDataBackupFile = new File(userDataBackupFilePath);
		}

		FileChannel sourceChannel = null;
		FileChannel destChannel   = null;
		try {
			sourceChannel = new FileInputStream(userDataFile)
				.getChannel();

			destChannel = new FileOutputStream(userDataBackupFile)
				.getChannel();

			destChannel.transferFrom(
				sourceChannel,
				0,
				sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}

	}

	/**
	 * Restore backup file.
	 *
	 * @throws IOException
	 *     Signals that an I/O exception has occurred.
	 */
	public static void restoreBackupFile() throws IOException {

		FileChannel sourceChannel = null;
		FileChannel destChannel   = null;
		try {
			sourceChannel = new FileInputStream(userDataBackupFile)
				.getChannel();

			destChannel = new FileOutputStream(userDataFile)
				.getChannel();

			destChannel.transferFrom(
				sourceChannel,
				0,
				sourceChannel.size());

		} finally {
			sourceChannel.close();
			destChannel.close();

			userDataBackupFile.delete();
		}

	}

}


