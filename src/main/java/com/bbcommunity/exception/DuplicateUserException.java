package com.bbcommunity.exception;
/*
* 사용자 중복 예외를 처리하는 클래스입니다.
* 이미 존재하는 이메일 또는 별명으로 회원가입을 시도할 경우 발생하는 예외를 처리합니다.
*/
public class DuplicateUserException extends RuntimeException{
	
	public DuplicateUserException() {
        super();
    }
	// 메시지와 원인 예외를 인자로 받는 생성자
	public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public DuplicateUserException(String message) {
        super(message);
    }
	
	public DuplicateUserException(Throwable cause) {
        super(cause);
    }
}
