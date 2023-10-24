package com.example.jwtserver.config;

import com.example.jwtserver.config.jwt.JwtAuthenticationFilter;
import com.example.jwtserver.config.jwt.JwtAuthorizationFilter;
import com.example.jwtserver.filter.MyFilter3;
import com.example.jwtserver.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UsersRepository usersRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);//filterConfig보다 빨리 실행됨
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션 사용안함
                .and()
                .addFilter(corsFilter)//@CorssOrigin(인증X) , 시큐리티필터에 등록 인증(0)
                .formLogin().disable()//form로그인 안씀
                .httpBasic().disable()//기본적인 http로그인 안씀
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))//AuthenticationManager를 파라미터 전달해야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),usersRepository))//AuthenticationManager를 파라미터 전달해야함
                .authorizeRequests()
                .antMatchers("/api/v1/users/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }
}
