package com.stylog.service;

import com.stylog.dto.SignupDto;
import com.stylog.entity.User;

public interface UserService {
	// 회원가입, 로그인 관련 추상 메서드
	void register(User user);
    void save(SignupDto signupDto);
    User login(String email, String password);
    boolean emailExists(String email);	// 이메일 중복 처리
    boolean nicknameExists(String nickname); // 닉네임 중복 처리 
    
}
