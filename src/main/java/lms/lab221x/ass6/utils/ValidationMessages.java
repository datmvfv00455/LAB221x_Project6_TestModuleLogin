package lms.lab221x.ass6.utils;

public class ValidationMessages {

	/** The Error Message If UserId Not Valid Role. */
	public static final String USER_ID_ROLE = 
			"User ID must be in sixteen (16) digits.";

	/** The Error Message If User Password Not Valid Role. */
	public static final String USER_PASSWORD_ROLE = 
			"Password must be eight (8) characters (alphanumeric).";

	/** The Error Message Attempt Left. */
	public static final String USER_ATTEMPT_LEFT = 
			"Invalid User ID/Password. Number of attempts left: ";
	
	/** The Error Message If User Is Not Exiested In DB. */
	public static final String USER_NOT_EXISTED =
			"Invalid User ID/Password.";

	/** The Error Message If User Is Lock. */
	public static final String USER_ATTEMPT_LEFT_LOCKID = 
			"The ID is lock and the user needs to call "
			+ "the Call Centre to unlock the User ID.";

	/** The Error Message If Not Answer Question 
	 * Or Answer More Than 03 Questions. */
	public static final String QUESTIONS_IS_EMPTY = 
			"Please answer at least one(1) of the hint question. "
			+ "Maximum question is three(3) questions.";

	/** The Error Message If Old Password Not Correct. */
	public static final String OLD_PASSWORD_NOT_CORRECT = 
			"Old password is not correct.";

	/** The Error Message If New Password Is Same Old Password. */
	public static final String NEW_PASSWORD_SAME_OLD_PASSWORD = 
			"New password is same as old password. "
			+ "Please change another password.";
 
	/** The Error Message If Confirm Message Not Match. */
	public static final String CONFIRM_PASSWORD_NOT_MATHCH = 
			"Confirm password does not match new password.";
	
}
