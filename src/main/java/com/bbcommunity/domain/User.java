package com.bbcommunity.domain;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String email;
	
	@Column(nullable=false)
    private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false)
	private String nickname;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
	
	@Column(nullable = false)
    private LocalDateTime regdate;
	
	// 생성자 메소드
	@Builder
    public User(String email, String password, String name, String gender, String nickname, Role role, LocalDateTime regdate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.nickname = nickname;
        this.role = role;
        this.regdate = regdate;
    }
	
	// 수정 메소드
	public User update(String name, String nickname) {
		this.name = name;
		this.nickname = nickname;
		return this;
	}
	
	// key 반환
    public String getRoleKey() {
        return this.role.getKey();
    }
    
    
    // 회원 생성
    public static User createUser(String email, String password, String name, String gender, 
    		String nickname, Role role, LocalDateTime regdate, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password)) //암호화한 pw를 pw로 사용
                .role(Role.USER)	//회원가입은 무조건 USER 계정만 만들어짐. 관리자는 따로 권한 부여해야함.
                .gender(gender)
                .nickname(nickname)
                .regdate(regdate)
                .build();
    }
}
