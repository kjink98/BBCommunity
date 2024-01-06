package com.bbcommunity.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bbcommunity.repository.UserRepository;
/*
* 사용자 인증을 위한 서비스 클래스입니다.
* 스프링 시큐리티의 UserDetailsService를 구현하여 사용자 인증에 필요한 메소드를 오버라이드합니다.
* @Component 어노테이션을 통해 스프링의 빈으로 등록되며, UserRepository를 주입 받아 사용합니다.
*/
@Component
public class MyUserDetailsService implements UserDetailsService{
	private UserRepository userRepository;

	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
    }
	/*
	* 사용자 이름(여기서는 이메일)로 사용자를 로드하는 메소드입니다.
	* UserRepository를 이용해 사용자를 찾고, 찾은 사용자의 정보를 UserDetails로 변환하여 반환합니다.
	* 사용자를 찾지 못할 경우 UsernameNotFoundException을 발생시킵니다.
	*/
	@Override 
	public UserDetails loadUserByUsername(String insertedEmail) throws UsernameNotFoundException {
        // 파라미터인 insertedId 부분에는 기존에 설정해두었던 
		// usernameParameter("id")에 해당하는 정보가 들어오게 된다. 
    	// 비밀번호가 동일한지 체크는 스프링시큐리티에서 알아서 진행하게 되므로 DB에서 아이디만 가져오면 된다.
		Optional<com.bbcommunity.entity.User> findByEmail = userRepository.findByEmail(insertedEmail); 
		com.bbcommunity.entity.User user = findByEmail.orElseThrow(
				() -> new UsernameNotFoundException("없는 회원입니다")
				);		
			
			
        // 사용자정의 User클래스의 빌더를 사용해
		// username에 아이디, password에 비밀번호, roles에 권한(역할)을 넣어주면 UserDetails가 리턴 된다.
		return User.builder()
		        .username(user.getEmail())
		        .password(user.getPassword())
		        .roles(user.getRole() != null ? user.getRole().toString() : "DEFAULT_ROLE")
		        .build();
		 
    }
}
