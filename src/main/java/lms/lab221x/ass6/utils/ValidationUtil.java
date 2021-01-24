package lms.lab221x.ass6.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import lms.lab221x.ass6.model.FistTimeLoginModel;
import lms.lab221x.ass6.model.QuestionModel;
import lms.lab221x.ass6.model.UserModel;
import lms.lab221x.ass6.utils.JsonResponse.Field;

public class ValidationUtil {

    /** The user ID pattern. */
    private static Pattern userIDPattern = Pattern
            .compile("^[0-9]{16}");

    /** The user password pattern. */
    private static Pattern userPasswordPattern = Pattern
            .compile("^(?=.*\\d)(?=.*[a-zA-Z]).{8}$");

    /**
     * Validate input user.
     *
     * @param inputUser
     *     the input user
     * 
     * @return the list contain 02 field :<br>
     *     errorOnIDField <br>
     *     errorOnPasswordField
     */
    public static List<Field> validateInputUser(UserModel inputUser) {

        List<Field>        errorFields          = new ArrayList<>();
        JsonResponse.Field errorOnIDField       = null;
        JsonResponse.Field errorOnPasswordField = null;

        String userId       = inputUser.getId();
        String userPassword = inputUser.getPassword();

        boolean isUserIDValidRole = (userId != null)
                ? userIDPattern.matcher(userId).matches()
                : false;

        boolean isUserPasswordValidRole = isPasswordValidRole(userPassword);

        String idFieldMsg = isUserIDValidRole
                ? null
                : ValidationMessages.USER_ID_ROLE;

        String passwordFieldMsg = isUserPasswordValidRole
                ? null
                : ValidationMessages.USER_PASSWORD_ROLE;

        errorOnIDField       = new Field("userId", idFieldMsg);
        errorOnPasswordField = new Field("userPassword", passwordFieldMsg);

        errorFields.add(errorOnIDField);
        errorFields.add(errorOnPasswordField);

        return errorFields;

    }

    /**
     * Validate first time login form.
     *
     * @param fistTimeLogin
     *     FistTimeLoginModel mapping by Object Mapper
     * 
     * @return the list contain 04 field :<br>
     *     errorOnQuestionsField <br>
     *     errorOnOldPassField<br>
     *     errorOnNewPassField <br>
     *     errorOnConfirmPassField
     */
    public static List<Field> validateFirstTimeLoginForm(
            FistTimeLoginModel fistTimeLogin) {

        LinkedList<Field>  errorFields             = new LinkedList<>();
        JsonResponse.Field errorOnQuestionsField   = null;
        JsonResponse.Field errorOnOldPassField     = null;
        JsonResponse.Field errorOnNewPassField     = null;
        JsonResponse.Field errorOnConfirmPassField = null;

        String questionsFieldMsg       = null;
        String oldPasswordFieldMsg     = null;
        String newPasswordFieldMsg     = null;
        String confirmPasswordFieldMsg = null;
        
        final int minAnsweredQuestion = Number.ONE;
        final int maxAnsweredQuestion = Number.THREE;

        List<QuestionModel> questionList = fistTimeLogin.getQuestionList();

        String oldPassword     = fistTimeLogin.getOldPassword();
        String newPassword     = fistTimeLogin.getNewPassword();
        String confirmPassword = fistTimeLogin.getConfirmPassword();

        // Check Questions Answers Field     
        boolean isValidQuestionCount =
                (questionList.size() >= minAnsweredQuestion)
                    && (questionList.size() <= maxAnsweredQuestion);
 
        questionsFieldMsg = isValidQuestionCount
                ? null
                : ValidationMessages.QUESTIONS_IS_EMPTY;

        // Check New Password
        boolean isPasswordValidRole = isPasswordValidRole(newPassword);
        if (isPasswordValidRole) {
            newPasswordFieldMsg = newPassword.equals(oldPassword)
                    ? ValidationMessages.NEW_PASSWORD_SAME_OLD_PASSWORD
                    : null;
        } else {
            newPasswordFieldMsg = ValidationMessages.USER_PASSWORD_ROLE;
        }

        // Check Confirm Password
        if (confirmPassword != null) {
            confirmPasswordFieldMsg = confirmPassword.equals(newPassword)
                    ? null
                    : ValidationMessages.CONFIRM_PASSWORD_NOT_MATHCH;
        }

        errorOnQuestionsField   = new Field("questionList", questionsFieldMsg);
        errorOnOldPassField     = new Field("oldPassword", oldPasswordFieldMsg);
        errorOnNewPassField     = new Field("newPassword", newPasswordFieldMsg);
        errorOnConfirmPassField = new Field("confirmPassword",
                confirmPasswordFieldMsg);

        errorFields.add(errorOnQuestionsField);
        errorFields.add(errorOnOldPassField);
        errorFields.add(errorOnNewPassField);
        errorFields.add(errorOnConfirmPassField);

        return errorFields;

    }

    /**
     * Checks if password is valid role.
     *
     * @param password
     *     the password
     * 
     * @return true, if password is valid role
     */
    private static boolean isPasswordValidRole(String password) {
        return (password != null)
                ? userPasswordPattern.matcher(password).matches()
                : false;
    }

    /**
     * Checks if errorFields have errors.
     *
     * @param errorFields
     *     the error fields
     * 
     * @return true, if errorFields have errors
     */
    public static boolean isHaveErrorFields(List<Field> errorFields) {

        for (Field field : errorFields) {
            if (field.getMessage() != null) {
                return true;
            }
        }
        return false;
    }
}
