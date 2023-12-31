package com.bbcommunity.entity;

import java.time.LocalDateTime;


import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bbcommunity.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter 자동으로 만듬, 추가적으로 RequiredArgsConstructor도
@NoArgsConstructor  // 인자 없는 생성자 만듬
@Entity 
@DynamicInsert // null 제외 쿼리 실행, 쿼리문 성능 향상
@Table(name = "member")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(unique = true, name = "user_email")
	private String email;
	
	@Column(nullable=false, name = "user_password")
    private String password;

	@Column(nullable=false, name = "user_name")
	private String name;

	@Column(nullable=false, name = "user_gender")
	private String gender;

	@Column(nullable=false, name = "user_nickname")
	private String nickname;

	@Enumerated(EnumType.STRING) // 열거형을 문자열로 저장합니다.
	@Column(nullable=false)
	private Role role;

	@Column(nullable=false, name = "user_regdate")
	private LocalDateTime regdate;

	// 생성자 메소드
	@Builder
	public User(Long id, String email, String password, String name, String gender, String nickname, Role role,
			LocalDateTime regdate) {
		// 사용자의 ID, 이메일, 비밀번호, 이름, 성별, 별명, 권한, 등록 날짜를 인자로 받는 생성자입니다.
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.nickname = nickname;
		this.role = role;
		this.regdate = regdate;
	}

	// 수정 메소드
	public User update(String name, String email, String password, PasswordEncoder passwordEncoder) {
		this.name = name;
		this.email = email;
		this.password = passwordEncoder.encode(password);
		return this;
	}

	// key 반환
	public String getRoleKey() {
		return this.role.getKey();
	}

	// 회원 생성
	public static User createUser(String email, String password, String name, String gender, String nickname, Role role,
			LocalDateTime regdate, PasswordEncoder passwordEncoder) {
		
		return User.builder().email(email).name(name).password(passwordEncoder.encode(password)) // 암호화한 pw를 pw로 사용
				.role(Role.USER) // 회원가입은 무조건 USER 계정만 만들어짐. 관리자는 따로 권한 부여해야함.
				.gender(gender).nickname(nickname).regdate(regdate).build();
	}
}
