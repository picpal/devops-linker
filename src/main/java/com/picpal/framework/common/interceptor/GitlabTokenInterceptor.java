package com.picpal.framework.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GitlabTokenInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String secretToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Gitlab-Token");

        if (token == null || !isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private boolean isValidToken(String token) {
        return secretToken.equals(token);
    }

}
