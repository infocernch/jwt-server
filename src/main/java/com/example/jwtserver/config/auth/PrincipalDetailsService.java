package com.example.jwtserver.config.auth;

import com.example.jwtserver.model.Users;
import com.example.jwtserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//http://localhost:9090/login 요청시 동작 => form로그인을 막았기 때문에 동작을 안함
//springSecurity 의 기본이 /login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        Users usersEntity = usersRepository.findByUsername(username);
        return new PrincipalDetails(usersEntity);
    }
}
