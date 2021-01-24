package lms.lab221x.ass6.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lms.lab221x.ass6.model.FistTimeLoginModel;
import lms.lab221x.ass6.model.QuestionModel;
import lms.lab221x.ass6.model.UserModel;
import lms.lab221x.ass6.service.UserService;
import lms.lab221x.ass6.utils.CoreUtil;
import lms.lab221x.ass6.utils.JsonResponse;
import lms.lab221x.ass6.utils.JsonResponse.Field;
import lms.lab221x.ass6.utils.URLUtil;
import lms.lab221x.ass6.utils.ValidationMessages;
import lms.lab221x.ass6.utils.ValidationUtil;

@Controller
public class FirstTimeController {

    /** The user service. */
    @Autowired
    UserService userService;

    /**
     * Get request for first time login page.
     *
     * @param session
     *     the session
     * 
     * @return if first time login return firsttime-login page,<br>
     *     else redirect to home page
     */
    @RequestMapping(URLUtil.URL_FIRST_TIME_LOGIN)
    public String fistTimeLogin(HttpSession session) {

        boolean isFistLogin = (boolean) session
                .getAttribute(CoreUtil.FIRST_LOGIN_SESSION);

        return isFistLogin
                ? URLUtil.JSP_FIRST_TIME_LOGIN
                : "redirect:" + URLUtil.URL_HOME;
    }

    /**
     * Post request for fist time login form.
     *
     * @param fistTimeLogin
     *     FistTimeLoginModel object mapping by Jackson
     * @param session
     *     the session
     * 
     * @return JSON
     */
    @PostMapping(value = URLUtil.URL_FIRST_TIME_LOGIN, 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse processForm(
            @RequestBody FistTimeLoginModel fistTimeLogin,
            HttpSession session) {

        List<Field> fistTimeLoginFields;
        String      userId;
        String      oldPassword;
        Field       oldPasswordErrorField;
        boolean     isHaveErrorOnInput;

        // Get Input Error Fields After Validate Input
        fistTimeLoginFields = ValidationUtil
                .validateFirstTimeLoginForm(fistTimeLogin);

        // Get UserId From Session
        userId = session
                .getAttribute(CoreUtil.USER_ID_SESSION).toString();

        // Check If Old Password Is Right In DataBase
        // If Old Password Is Wrong
        // Update Error Message Of OldPassword Field
        oldPassword           = fistTimeLogin.getOldPassword();
        oldPasswordErrorField = fistTimeLoginFields.get(1);
        validateOldPasswordField(
                userId,
                oldPassword,
                oldPasswordErrorField);

        isHaveErrorOnInput = ValidationUtil
                .isHaveErrorFields(fistTimeLoginFields);

        return isHaveErrorOnInput
                ? haveErrorOnInput(fistTimeLoginFields)
                : notHaveErrorOnInput(
                        fistTimeLogin,
                        userId,
                        session,
                        fistTimeLoginFields);

    }

    private void validateOldPasswordField(
            String userID,
            String oldPassword,
            Field oldPasswordErrorField) {

        UserModel oldUser = UserModel.builder()
                .id(userID)
                .password(oldPassword)
                .build();

        boolean isRightPassword = userService.isRightPassword(oldUser);

        String oldPasswordFieldMsg = isRightPassword
                ? null
                : ValidationMessages.OLD_PASSWORD_NOT_CORRECT;

        oldPasswordErrorField.setMessage(oldPasswordFieldMsg);

    }

    private JsonResponse notHaveErrorOnInput(
            FistTimeLoginModel fistTimeLogin,
            String userId,
            HttpSession session,
            List<Field> inputFistTimeLoginFields) {

        List<QuestionModel> questionList;
        String              confirmPassword;
        boolean             isUpdateSuccess;

        questionList    = fistTimeLogin.getQuestionList();
        confirmPassword = fistTimeLogin.getConfirmPassword();

        // update information to database
        isUpdateSuccess = userService.updateFistTimeLogin(
                userId,
                questionList,
                confirmPassword);

        return CoreUtil.buildJsonResponse(
                isUpdateSuccess,
                null,
                URLUtil.JSP_LOGIN,
                inputFistTimeLoginFields);

    }

    private JsonResponse haveErrorOnInput(List<Field> errorFields) {
        return CoreUtil.buildJsonResponse(
                false,
                null,
                null,
                errorFields);
    }

}
