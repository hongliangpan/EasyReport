package com.easytoolsoft.easyreport.web.filter;

import com.easytoolsoft.easyreport.web.util.ConfigUtils;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    public static final String USER_CHECK = "user.check";
    public static final String USER = "user";
    public static final String TOKEN = "token";

    public static boolean isCheckUser() {
        String check = ConfigUtils.getValue(USER_CHECK);
        return !Strings.isNullOrEmpty(check) && "true".equals(check);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isCheckUser()) {
            return true;
        }
        String user = request.getParameter(USER);
        String token = request.getParameter(TOKEN);
        if (user == null && request.getSession().getAttribute(USER) != null) {
            user = request.getSession().getAttribute(USER).toString();
        }
        if (token == null && request.getSession().getAttribute(TOKEN) != null) {
            token = request.getSession().getAttribute(TOKEN).toString();
        }

        if (StringUtils.isBlank(token) || StringUtils.isBlank(user)) {
            return false;
        }
        if (user.equals(ConfigUtils.getValue(USER)) && token.equals(ConfigUtils.getValue(TOKEN))) {
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(TOKEN, token);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}