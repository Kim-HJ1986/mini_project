package com.coffee.miniproject.security;

import com.coffee.miniproject.security.filter.RestLoginFailureHandler;
import com.coffee.miniproject.security.filter.RestLoginSuccessHandler;
import com.coffee.miniproject.security.filter.RestLogoutSuccessHandler;
import com.coffee.miniproject.security.filter.RestUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.MalformedURLException;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity //스프링 시큐리티가 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OAuthUserDeatilsServiceImpl oAuthUserDeatilsService;

    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;



//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/h2-console/**");
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);
//        http
//                .cors()
//                .and()
//                .authorizeHttpRequests((authz) -> authz
////                        .antMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
////                        .antMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
////                        .antMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
//                        .antMatchers("/admin/**").access(hasRole("ROLE_ADMIN"))
//                        .anyRequest().permitAll()
//                )
//                .csrf().disable()
//
//                .formLogin().disable()
////                    .loginPage("/user/login")
////                    .loginProcessingUrl("/user/login")
////                    .defaultSuccessUrl("/api/posts")
////                    .permitAll()
//                .logout()
//                    .logoutUrl("/user/logout")
//                    .logoutSuccessUrl("/api/posts")
//                    .deleteCookies("JSESSIONID")
//                    .permitAll()
//                    .and()
//                .oauth2Login()
//                    .loginPage("/user/login")
//                    .userInfoEndpoint()
//                    .userService(oAuthUserDeatilsService)
//        ;
//        return http.build();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .cors()
                .configurationSource(corsConfigurationSource());
        //.ignoringAntMatchers("/user/**"); // 1개1개 직접 예외요청 적용


        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
//                .antMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
//                .antMatchers("/admin/**").access(hasRole("ROLE_ADMIN"))
                .anyRequest().permitAll();

        // [로그인 기능]
        http.formLogin().disable();
        http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);

        // [로그아웃 기능]
        http.logout()
                // 로그아웃 요청 처리 URL
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(restLogoutSuccessHandler)
                .permitAll();
        http.exceptionHandling();

        // "접근 불가" 페이지 URL 설정
//                .accessDeniedPage("/forbidden.html");


    }

    protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter(){
        RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
        try{
            authFilter.setFilterProcessesUrl("/user/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
            authFilter.setUsernameParameter("username");
            authFilter.setPasswordParameter("password");
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setAuthenticationSuccessHandler(successHandler());
            authFilter.setAuthenticationFailureHandler(failureHandler());

        } catch (Exception e){
            e.printStackTrace();
        }
        return authFilter;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*"); // 배포 전 모두 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new RestLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new RestLoginFailureHandler();
    }
}
