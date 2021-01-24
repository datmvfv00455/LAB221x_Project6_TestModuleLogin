package lms.lab221x.ass6.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import lms.lab221x.ass6.service.UserService;
import lms.lab221x.ass6.utils.CoreUtil;
import lms.lab221x.ass6.utils.URLUtil;

public class AuthorizationInterceptor implements HandlerInterceptor {

    /** The user service. */
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {

        System.out.println();
        printlnTab("------ AuthorizationInterceptor.preHandle ------");
        printlnTab("Request URL: " + request.getRequestURL());
        printlnTab("Session ID: " + request.getSession().getId());

        String userID = (String) request
                .getSession()
                .getAttribute(CoreUtil.USER_ID_SESSION);

        if (userID == null) {
            printlnTab("!!! Not Login, Redirect to Login Page !!!");
            response.sendRedirect(URLUtil.JSP_LOGIN);
            return false;
        } else {
            printlnTab("!!! Login With UserID: " + userID + " !!!");
            return true;
        }

    }

    private void printlnTab(String s) {
        System.out.println("\t" + s);
    }

}
