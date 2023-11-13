package com.example.reuseit_project.controller;

import com.example.reuseit_project.config.auth.PrincipalDetails;
import com.example.reuseit_project.model.User;
import com.example.reuseit_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
        System.out.println("/test/oauth/login ==================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("oauth2User : " + oauth.getAttributes());
        return "OAuth 세션 확인하기";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.getUser());
        System.out.println("userDetails : " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    // OAuth로 로그인, 일반 로그인 모두 PrincipalDetails로 받아서 처리할 수 있다.
    @GetMapping({"/user"})
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
    }

    @GetMapping({"/admin"})
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping({"/manager"})
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping({"/loginForm"})
    public String login() {
        return "loginForm";
    }

    @GetMapping({"/joinForm"})
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping({"/join"})
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rowPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rowPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() { //이 어노테이션은 특정 메소드에만 접근 제한을 걸수 있는 어노테이션. (/info로 접근하기 위해서는 역할이 ROLE_ADMIN 이어야 함.)
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 정보";
    }
}
