package com.coffee.miniproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity //스프링 시큐리티가 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private GoogleUserDeatilsServiceImpl googleUserDeatilsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
//                        .antMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
//                        .antMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
//                        .antMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
                        .antMatchers("/admin/**").access(hasRole("ROLE_ADMIN"))
                        .anyRequest().permitAll()
                )
                .csrf().disable()
                .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/user/login")
                    .defaultSuccessUrl("/api/posts")
                    .permitAll()
                    .and()
                .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/api/posts")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                .oauth2Login()
                    .loginPage("/user/login")
                    .userInfoEndpoint()
                    .userService(googleUserDeatilsService)



        ;
        return http.build();
    }
}
