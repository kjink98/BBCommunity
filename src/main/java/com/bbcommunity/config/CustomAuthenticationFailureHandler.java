package com.bbcommunity.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
* 이 클래스는 인증 실패 시 사용자에게 반환되는 동작을 정의하는 클래스입니다.
* AuthenticationFailureHandler 인터페이스를 구현함으로써 인증 실패 시의 동작을 사용자 정의합니다.
*/
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	/*
    * onAuthenticationFailure 메서드는 인증이 실패했을 때 호출됩니다.
    * HttpServletRequest, HttpServletResponse, AuthenticationException를 인자로 받아 처리합니다.
    * 이 메서드에서는 인증 실패 시 사용자에게 반환되는 HTTP 응답 상태 코드와 메시지를 정의합니다.
    */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
    	// 인증 실패 시 HTTP 응답 상태 코드를 401(Unauthorized)로 설정합니다.
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	
    	PrintWriter writer = response.getWriter();
        writer.write("{ \"message\": \"LOGIN ERROR\" }");
        writer.flush();
    }
}
