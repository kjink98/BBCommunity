package com.bbcommunity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bbcommunity.entity.User;
import com.bbcommunity.role.Role;

import jakarta.transaction.Transactional;

/*
* 사용자 정보를 처리하는 레포지토리 인터페이스입니다.
* 이메일, ID를 이용한 사용자 조회, 비밀번호 및 닉네임 업데이트, 사용자 탈퇴 및 삭제, 사용자 권한 업데이트 등의 메소드를 제공합니다.
* JpaRepository 인터페이스를 상속받아 기본적인 CRUD 연산을 지원합니다.
*/
public interface UserRepository extends JpaRepository<User, Long> {
	// 이메일을 이용하여 사용자 정보를 조회하는 메소드입니다.
	Optional<User> findByEmail(String email);

	// 사용자 ID를 이용하여 사용자 정보를 조회하는 메소드입니다.
	Optional<User> findById(Long userId);

	// 사용자 ID와 새로운 비밀번호를 이용하여 사용자의 비밀번호를 업데이트하는 메소드입니다.
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
	void updateUserPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

	// 사용자 ID와 새로운 닉네임을 이용하여 사용자의 정보를 업데이트하는 메소드입니다.
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.nickname = :newNickname WHERE u.id = :userId")
	void updateInfo(@Param("userId") Long userId, @Param("newNickname") String newNickname);

	// 이메일을 이용하여 사용자를 탈퇴시키는 메소드입니다.
	@Transactional
	@Modifying
	@Query("DELETE FROM User u WHERE u.email = :email")
	int resignUser(@Param("email") String email);

	// 사용자 ID를 이용하여 사용자를 삭제하는 메소드입니다.
	@Transactional
	@Modifying
	@Query("DELETE FROM User u WHERE u.id = :userId")
	int deleteUser(@Param("userId") Long userId);

	// 모든 사용자 정보를 조회하는 메소드입니다.
	List<User> findAll();

	// 이메일과 권한을 이용하여 사용자의 권한을 업데이트하는 메소드입니다.
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.role = ?2 WHERE u.email = ?1")
	void updateRoles(String email, Role role);
}
