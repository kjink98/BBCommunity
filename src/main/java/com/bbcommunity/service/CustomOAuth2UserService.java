package com.bbcommunity.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bbcommunity.dto.OAuthAttributes;
import com.bbcommunity.dto.SessionUser;
import com.bbcommunity.entity.User;
import com.bbcommunity.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
/**
 * OAuth2 로그인을 처리하기 위한 서비스 클래스
 */
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	private final UserRepository userRepository;
	private final HttpSession httpSession;
	private final PasswordEncoder passwordEncoder;
	
	/**
     * OAuth2 로그인을 통해 사용자 정보를 로드하는 메소드
     * 로드된 사용자 정보는 OAuth2User 객체로 반환
     */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		// 현재 진행중인 서비스를 구분하기 위해 문자열로 받음.
		// oAuth2UserRequest.getClientRegistration().getRegistrationId()에 값이 들어있다.
		// {registrationId='naver'} 이런식으로
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		// OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다. 각각
		// 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();

		// OAuth2 로그인을 통해 가져온 OAuth2User의 attribute를 담아주는 of 메소드.
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
				oAuth2User.getAttributes());

		User user = saveOrUpdate(attributes);
		System.out.println("custom"+user);
		/* 세션 정보를 저장하는 직렬화된 dto 클래스 */
		httpSession.setAttribute("user", new SessionUser(user));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
				attributes.getAttributes(), attributes.getNameAttributeKey());
	}
	
	 /**
     * OAuth2 로그인을 통해 받은 사용자 정보를 데이터베이스에 저장하거나 업데이트하는 메소드
     * 기존에 동일한 이메일을 가진 사용자가 있다면 해당 사용자 정보를 업데이트하고,
     * 없다면 새로운 사용자 정보를 생성하여 데이터베이스에 저장
     */
	private User saveOrUpdate(OAuthAttributes attributes) {
			
	    // 이메일을 기반으로 기존 멤버를 조회
		User user = userRepository.findByEmail(attributes.getEmail())
				//존재할경우 키,값으로 값을 관리하는 OAuth에 맞춰 업데이트 수행
				.map(entity -> entity.update(
							attributes.getName(),
							attributes.getEmail(),
							attributes.getNameAttributeKey(),
							passwordEncoder
							))
				//존재하지 않는 신규 로그인일 경우 OAuthAttribute의 toEntity 메서드 활용
				.orElse(attributes.toEntity());
		
		//그렇게 처리한 데이터를 리포지토리 저장
		return userRepository.save(user);
	}
}
