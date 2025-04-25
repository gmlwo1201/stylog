package com.stylog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stylog.dto.SignupDto;
import com.stylog.entity.User;
import com.stylog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder; // 비밀번호 암호화 위함
	
	@Autowired	// 생성자에서 의존성 주입
	UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void register(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("이미 사용 중인 이메일입니다.");
		}
	}

	@Override
	public void save(SignupDto signupDto) {
	    User user = signupDto.toEntity(passwordEncoder);
	    userRepository.save(user);
	}

	@Override
	public User login(String email, String password) {
		User user = userRepository.findByEmail(email);
		// 입력된 비밀번호(password)와 저장된 해시 비교
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
	}

	@Override
	public boolean emailExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

	@Override
	public boolean nicknameExists(String nickname) {
		return userRepository.findByNickname(nickname) != null;
	}

}
