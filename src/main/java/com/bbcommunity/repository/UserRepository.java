package com.bbcommunity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
