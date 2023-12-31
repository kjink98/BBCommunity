package com.bbcommunity.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.bbcommunity.entity.User;
import com.bbcommunity.role.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
/**
 * OAuth 인증과 관련된 사용자 정보를 관리하는 DTO(Data Transfer Object) 클래스
 */
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth에서 받아온 사용자 정보를 담을 맵
    private String nameAttributeKey; // 사용자 이름 속성 키
    private String name; // 사용자 이름
    private String id; // 사용자 ID
    private String email; // 사용자 이메일
    private String gender; // 사용자 성별

	@Builder
	/**
	 * OAuthAttributes 객체를 생성하는 빌더 메소드
	 */
	public OAuthAttributes(Map<String, Object> attributes, 
			String nameAttributeKey, String name, String email,
			String gender, String id) {
		super();
        this.attributes = attributes; // 받아온 속성들을 DTO에 매핑
        this.nameAttributeKey = nameAttributeKey; // 이름 속성 키 초기화
        this.name = name; // 이름 초기화
        this.email = email; // 이메일 초기화
        this.gender = gender; // 성별 초기화
        this.id = id; // ID 초기화
	}

	// 네이버 카카오 구글마다 지원하는 API Attribute들이 다름. registrationId로 해당하는 API 메서드를 호출하는 방식으로
	// 하였음.
	// 일단 구글만
	// OAuth2User에서 반환하는 사용자 정보는 Map 형태이므로 값 하나하나를 변환
	
	/**
	 * OAuth2User에서 반환하는 사용자 정보는 Map 형태이므로 값 하나하나를 변환하는 메소드입니다.
	 * 현재 구글에 대한 메소드만 구현되어 있습니다.
	 * 네이버 카카오 구글마다 지원하는 API Attribute들이 다름
	 */
	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
			Map<String, Object> attributes) {
		if("naver".equals(registrationId)) {
            return ofNaver("id", attributes); // 네이버 속성 매핑
		}
		else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes); // 카카오 속성 매핑
        }
		return ofGoogle(userNameAttributeName, attributes); // 구글 속성 매핑
	}
	
	/**
	 * Google의 OAuth 사용자 정보를 변환하는 메소드입니다.
	 */
	
	private static OAuthAttributes ofGoogle(String userNameAttributeName, 
			Map<String, Object> attributes) {
		System.out.println(attributes.get("name"));

		return OAuthAttributes.builder()
				.id((String) attributes.get("sub"))
				.name((String) attributes.get("name"))
				.email((String) attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	/**
	 * Naver의 OAuth 사용자 정보를 변환하는 메소드입니다.
	 */
	 
	private static OAuthAttributes ofNaver(String userNameAttributeName,
            Map<String, Object> attributes){
				Map<String, Object> response = (Map<String, Object>) attributes.get("response");
				System.out.println(response.get("id"));
				
				return OAuthAttributes.builder()
					.id((String) response.get("id"))
					.name((String) response.get("name"))
					.email((String) response.get("email"))
					.gender((String) response.get("gender"))
					.attributes(response)
					.nameAttributeKey(userNameAttributeName)
					.build();
			}
	/**
	 * Kakao의 OAuth 사용자 정보를 변환하는 메소드입니다.
	*/
	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoAccount  = (Map<String, Object>) attributes.get("kakao_account");
        
        return OAuthAttributes.builder()
                .id(String.valueOf(attributes.get("id")))
                .email((String) kakaoAccount.get("email"))
                .name((String) properties.get("nickname"))
                .gender(String.valueOf(kakaoAccount.get("gender")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
     
	
	/**
	 * User Entity를 생성하는 메소드입니다.
	 * 소셜 로그인(Google, 카카오, 네이버)을 통해 가입하는 사용자의 정보를 추출하여 User 객체를 생성합니다.
	 * 처음 가입할 때 호출되며, 기본적으로 USER 권한을 부여합니다.
	 */
	public User toEntity() {
		String userId = "";
		String userGender ="";
		String userEmail = "";
		String userNickname = "";
		// Google에서 제공하는 고유 ID를 가져옵니다.
	    if (attributes.containsKey("sub")) {
	        userEmail = (String) attributes.get("email");
	        userGender = "비공개";
	        userNickname = (String) attributes.get("name");
	    } 
	    // 카카오에서 제공하는 고유 ID 및 성별 정보를 가져옵니다.
	    else if (attributes.containsKey("kakao_account"))  {  
	    	Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
	    	Map<String,Object> properties = (Map<String, Object>) attributes.get("properties");
	        userId = String.valueOf(attributes.get("id"));
	        userGender = String.valueOf(kakaoAccount.get("gender")); // 수정된 부분
	        userEmail = (String) kakaoAccount.get("email");
	        userNickname = (String) properties.get("nickname");
	    }  
	    // Naver에서 제공하는 고유 ID를 가져옵니다.
	    else{  
	    	userId = (String) attributes.get("id");
	    	userGender = (String) attributes.get("gender"); // Naver에서 제공하는 성별 정보를 가져옵니다.
	    	userEmail = (String) attributes.get("email");
	    	userNickname = (String) attributes.get("nickname");
	    }
	 // 추출한 정보를 바탕으로 User 객체를 생성합니다. 비밀번호는 "N/A"로 저장되며, 기본 권한은 USER입니다.
	    return User.builder()
	    	    .email(userEmail)
	    	    .name(name)
	    	    .nickname(userNickname)
	    	    .gender(userGender)
	    	    .password("N/A")
	    	    .role(Role.USER)
	    	    .regdate(LocalDateTime.now())
	    	    .build();
	}
}
