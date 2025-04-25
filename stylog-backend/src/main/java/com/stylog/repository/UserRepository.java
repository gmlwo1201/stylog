package com.stylog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stylog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	// 이메일 중복 확인
	boolean existsByEmail(String email); 
	User findByEmail(String email);
	
	// 닉네임 중복 확인
    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);
}
