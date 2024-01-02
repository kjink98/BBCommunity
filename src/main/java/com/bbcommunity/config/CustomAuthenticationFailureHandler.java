package com.bbcommunity.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	
    	PrintWriter writer = response.getWriter();
        writer.write("{ \"message\": \"LOGIN ERROR\" }");
        writer.flush();
    }
}
