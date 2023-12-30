package com.bbcommunity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bbcommunity.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	
	@Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :userId")
    void updateUserPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

	@Transactional
    @Modifying
    @Query("UPDATE User u SET u.nickname = :newNickname WHERE u.id = :userId")
    void updateInfo(@Param("userId") Long userId, @Param("newNickname") String newNickname);
	
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.email = :email")
    int resignUser(@Param("email") String email);
}
