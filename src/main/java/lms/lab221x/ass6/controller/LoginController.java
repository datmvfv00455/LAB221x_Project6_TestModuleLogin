package lms.lab221x.ass6.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import lms.lab221x.ass6.model.UserModel;
import lms.lab221x.ass6.service.UserService;
import lms.lab221x.ass6.utils.CoreUtil;
import lms.lab221x.ass6.utils.JsonResponse;
import lms.lab221x.ass6.utils.JsonResponse.Field;
import lms.lab221x.ass6.utils.Number;
import lms.lab221x.ass6.utils.URLUtil;
import lms.lab221x.ass6.utils.ValidationMessages;
import lms.lab221x.ass6.utils.ValidationUtil;

@Controller
public class LoginController {

    /** The user service. */
    @Autowired
    UserService userService;

    /**
     * Get request for default page
     * and login page.
     *
     * @return login.jsp
     */
    @GetMapping(value = {
            URLUtil.URL_DEFUAL,
            URLUtil.URL_LOGIN
    })
    public String login() {
        return URLUtil.JSP_LOGIN;

    }

    /**
     * Get request for logout.
     *
     * @param session
     *     the session
     * 
     * @return the redirect view login.jsp
     */
    @GetMapping(URLUtil.URL_LOGOUT)
    public String logout(HttpSession session) {
        session.removeAttribute(CoreUtil.USER_ID_SESSION);
        return "redirect:" + URLUtil.URL_LOGIN;
    }

    /**
     * Get request for logout home page.
     *
     * @return home.jsp
     */
    @GetMapping(URLUtil.URL_HOME)
    public String home() {

        return URLUtil.JSP_HOME;

    }

    /**
     * Post request for login form.
     *
     * @param inputUser
     *     UserModel object mapping by Jackson
     * @param session
     *     the session
     * 
     * @return JSON
     */
    @PostMapping(value = URLUtil.URL_LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResponse processForm(
            @RequestBody UserModel inputUser,
            HttpSession session) {

        List<Field> inputUserFields;
        boolean     isHaveErrorOnInput;

        // Validate Form Input
        inputUserFields = ValidationUtil.validateInputUser(inputUser);

        // Check if have error in input.
        isHaveErrorOnInput = ValidationUtil.isHaveErrorFields(inputUserFields);

        return isHaveErrorOnInput
                ? haveErrorOnInput(inputUserFields)
                : notHaveErrorOnInput(
                        inputUser,
                        session,
                        inputUserFields);

    }

    private JsonResponse notHaveErrorOnInput(
            UserModel inputUser,
            HttpSession session,
            List<Field> errorInputUserFields) {

        String jsonMsg     = null;
        String inputUserID = inputUser.getId();

        // Step01. Check if user is valid
        boolean isValidUser = userService.isValidUser(inputUser);

        // Step02. Get Attemp Left
        int attemptLeft = userService.getAttemptLeft(inputUserID);

        if (isValidUser) {

            // if valid user, but attemptLeft = 0
            // Still Lock ID
            if (attemptLeft == 0) {
                jsonMsg = ValidationMessages.USER_ATTEMPT_LEFT_LOCKID;

                return CoreUtil.buildJsonResponse(
                        false,
                        jsonMsg,
                        null,
                        errorInputUserFields);
            }

            // Step03. Check If first login
            boolean isFistLogin = userService.isFirstLogin(inputUserID);

            // Add Session
            session.setAttribute(CoreUtil.USER_ID_SESSION, inputUserID);
            session.setAttribute(CoreUtil.FIRST_LOGIN_SESSION, isFistLogin);

            // Step04. Set redirectedUrl
            String redirectedUrl = isFistLogin
                    ? URLUtil.JSP_FIRST_TIME_LOGIN
                    : URLUtil.JSP_HOME;

            return CoreUtil.buildJsonResponse(
                    true,
                    null,
                    redirectedUrl,
                    errorInputUserFields);
        }

        // If user not valid
        if (attemptLeft == Number.NEGATIVE_ONE) {
            jsonMsg = ValidationMessages.USER_NOT_EXISTED;
        } else if (attemptLeft == Number.ZERO) {
            jsonMsg = ValidationMessages.USER_ATTEMPT_LEFT_LOCKID;
        } else {
            jsonMsg = ValidationMessages.USER_ATTEMPT_LEFT + attemptLeft;
        }

        return CoreUtil.buildJsonResponse(
                false,
                jsonMsg,
                null,
                errorInputUserFields);

    }

    private JsonResponse haveErrorOnInput(List<Field> errorFields) {
        return CoreUtil.buildJsonResponse(
                false,
                null,
                null,
                errorFields);
    }

}
