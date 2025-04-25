package com.stylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylog.dto.LoginDto;
import com.stylog.dto.SignupDto;
import com.stylog.entity.User;
import com.stylog.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/stylog")
public class UserController {
	private final UserService userService;
	
	// 생성자 1개만 있으면 @Autowired 어노테이션 생략 가능(자동 적용)
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/sign_up")
	public String signupForm(Model model) {
		model.addAttribute("user", new User());
		return "sign_up";
	}
	
	// JSR-380 (Bean Validation)으로 유효성 자동 체크
	@PostMapping("/signup")
	public String signupSubmit(@Valid @ModelAttribute("user") SignupDto signupDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "빈 칸 없이 입력해주세요.");
	        return "sign_up";	// 에러 시 다시 회원가입 페이지
	    }

	    // 이메일 중복 체크
	    if (userService.emailExists(signupDto.getEmail())) {
	        model.addAttribute("errorMessage", "이미 가입된 이메일입니다.");
	        return "sign_up";
	    }
	    
	    // 닉네임 중복 체크
	    if (userService.nicknameExists(signupDto.getNickname())) {
	        model.addAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
	        return "sign_up";
	    }

	    userService.save(signupDto); // 실제 저장 로직
	    return "redirect:/stylog/login";
	}
	
	@GetMapping("/login")
	public String loginForm(Model model) {
	    model.addAttribute("user", new User());
	    return "login";
	}
	
	@PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") LoginDto loginDto, BindingResult bindingResult, HttpSession session, Model model) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
			model.addAttribute("loginError", "빈 칸 없이 입력해주세요.");
	        return "login";	// 에러 시 다시 로그인 페이지
	    }
        User loginUser = userService.login(loginDto.getEmail(), loginDto.getPassword());
        if (loginUser != null) {
            session.setAttribute("user", loginUser); // 로그인 정보 세션에 저장
            return "redirect:/stylog/index"; // 로그인 성공 시 메인으로
        } else {
            model.addAttribute("loginError", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }
	
	@GetMapping("/index")
	public String mainPage(HttpSession session, Model model) {
	    User user = (User) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/login";
	    }
	    model.addAttribute("nickname", user.getNickname());
	    return "index"; // templates/index.html
	}
	
	 @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login";
	    }
}