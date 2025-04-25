package com.stylog.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.stylog.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    // 기본 생성자
    public SignupDto() {}
    
    // toEntity 메서드로 SignupDto를 User Entity로 변환
    public User toEntity(PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(this.username);
        user.setNickname(this.nickname);
        user.setEmail(this.email);
        user.setPassword(encoder.encode(this.password)); // 비밀번호 암호화
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }

    // 게터/세터
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
