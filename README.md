# stylog
## 구현 내용
* 로그인, 회원 가입 기능 구현
  - 회원 가입 페이지에서 이름, 닉네임, 이메일, 비밀번호, 전화번호를 입력하면 DB에 저장됨
  - **이메일, 비밀번호 중 하나라도 입력하지 않으면 오류 메세지 출력하고 return**
  - **DB에 이미 저장되어있는 이메일을 입력하면 오류메세지 출력**
```java
@PostMapping("/signup")
	public String signupSubmit(@ModelAttribute("user") User user, Model model) {

	    // email, password 입력했는지 확인
	    if (user.getEmail() == null || user.getEmail().isEmpty()) {
	        model.addAttribute("errorMessage", "이메일을 입력해주세요.");
	        return "signup";
	    }
	    if (user.getPassword() == null || user.getPassword().isEmpty()) {
	        model.addAttribute("errorMessage", "비밀번호를 입력해주세요.");
	        return "signup";
	    }

	    // 이메일 중복 체크
	    if (userService.emailExists(user.getEmail())) {
	        model.addAttribute("errorMessage", "이미 가입된 이메일입니다.");
	        return "signup";
	    }

	    userService.save(user); // 저장
	    return "redirect:/login";
	}
```
  - 회원가입에 모든 정보를 올바르게 기입 > 회원가입 버튼/이미 회원가입 완료 > 로그인 버튼 누르면 로그인 페이지로
  - 로그인에서 입력한 정보가 DB에 저장된 값과 일치하는 것이 없다면 오류 메세지 출력
```java
// 로그인 시 유효성 검사
	@PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
        User loginUser = userService.login(user.getEmail(), user.getPassword());

        if (loginUser != null) {
            session.setAttribute("user", loginUser); // 로그인 정보 세션에 저장
            return "redirect:/"; // 로그인 성공 시 메인으로
        } else {
            model.addAttribute("loginError", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }
```
