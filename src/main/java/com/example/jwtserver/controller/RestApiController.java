package com.example.jwtserver.controller;

import com.example.jwtserver.config.auth.PrincipalDetails;
import com.example.jwtserver.model.Users;
import com.example.jwtserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>home</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setRoles("ROLE_USER");
        usersRepository.save(users);
        return "회원 가입 완료";
    }

    @GetMapping("/api/v1/users")
    public String users(Authentication authentication) {//user,manager,admin 권한만 접근가능
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails = " + principalDetails);
        return "users";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {//admin 권한만 접근가능
        return "admin";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {//admin, manager 권한만 접근가능
        return "manager";
    }
}
