package com.example.happyDream.Controller;

import com.example.happyDream.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String reviewSelectAll(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "userManagement";
    }

    @GetMapping("/signin")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "signin";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(String username, String password, String passwordConfirm, Model model) {
        // 서버 측 유효성 검사
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "아이디를 입력해주세요.");
            return "signup";
        }
        if (password == null || password.length() < 4) {
            model.addAttribute("error", "비밀번호는 4글자 이상이어야 합니다.");
            return "signup";
        }
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        try {
            userService.userInsert(username, password, (byte) 2);
            return "redirect:/signin"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup"; // 에러 발생 시 회원가입 페이지로 다시 이동
        } catch (Exception e) {
            model.addAttribute("error", "서버 오류가 발생했습니다.");
            return "signup"; // 기타 오류 시 회원가입 페이지로 다시 이동
        }
    }
}
