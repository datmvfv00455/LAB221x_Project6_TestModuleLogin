package lms.lab221x.ass6.utils;

import java.util.List;

import lms.lab221x.ass6.utils.JsonResponse.Field;

public class CoreUtil {

	/** The Constant USER_ID_SESSION. */
	public static final String USER_ID_SESSION = "UserIDSession";

	/** The Constant FIRST_LOGIN_SESSION. */
	public static final String FIRST_LOGIN_SESSION = "FirstLoginSession";

	/**
	 * Build the json response object.
	 *
	 * @param isValid
	 *     the is valid
	 * @param messege
	 *     the messege
	 * @param redirectedUrl
	 *     the redirected url
	 * @param errorFields
	 *     the error fields
	 * 
	 * @return the json response
	 */
	public static JsonResponse buildJsonResponse(
		boolean isValid,
		String messege,
		String redirectedUrl,
		List<Field> errorFields) {

		return JsonResponse.builder()
			.isValid(isValid)
			.messege(messege)
			.redirectUrl(redirectedUrl)
			.errorFields(errorFields)
			.build();

	}

}
