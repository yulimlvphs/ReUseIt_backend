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

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping({"/user"})
    public @ResponseBody String user() {
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
