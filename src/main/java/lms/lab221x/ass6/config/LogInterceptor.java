package lms.lab221x.ass6.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {

        System.out.println("\n\t=====================================");

        System.out.println();
        printlnTab("------ LogInterception.preHandle ------");
        printlnTab("Request URL: " + request.getRequestURL());
        printlnTab("Session ID: " + request.getSession().getId());
        printlnTab("Start Time: " + System.currentTimeMillis());

        request.setAttribute("startTime", System.currentTimeMillis());

        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex)
            throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long endTime   = System.currentTimeMillis();

        System.out.println();
        printlnTab("------ LogInterception.afterCompletion ------");
        printlnTab("Request URL: " + request.getRequestURL());
        printlnTab("Session ID: " + request.getSession().getId());
        printlnTab("Start Time: " + System.currentTimeMillis());
        printlnTab("End Time:" + endTime);
        printlnTab("Time Taken:" + (endTime - startTime));

        System.out.println("\n\t=====================================");

    }

    private void printlnTab(String s) {
        System.out.println("\t" + s);
    }

}
