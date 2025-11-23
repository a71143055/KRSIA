package com.krsia.blog.controller;

import com.krsia.blog.entity.User;
import com.krsia.blog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal UserDetails user) {
        if (user != null) return "redirect:/";
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (userService.getByEmail(email) != null) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "register";
        }
        userService.register(name, email, password);
        model.addAttribute("message", "회원가입 성공! 로그인해주세요.");
        return "login";
    }
}
