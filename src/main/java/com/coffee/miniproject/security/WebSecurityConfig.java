package com.coffee.miniproject.security;

<<<<<<< Updated upstream
=======
import com.coffee.miniproject.security.filter.RestLoginFailureHandler;
import com.coffee.miniproject.security.filter.RestLoginSuccessHandler;
import com.coffee.miniproject.security.filter.RestLogoutSuccessHandler;
import com.coffee.miniproject.security.filter.RestUsernamePasswordAuthenticationFilter;
import com.coffee.miniproject.security.jwt.JwtAccessDeniedHandler;
import com.coffee.miniproject.security.jwt.JwtAuthenticationEntryPoint;
import com.coffee.miniproject.security.jwt.JwtSecurityConfig;
import com.coffee.miniproject.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
<<<<<<< Updated upstream
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
=======
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

>>>>>>> Stashed changes

@Configuration
@EnableWebSecurity //스프링 시큐리티가 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true)
<<<<<<< Updated upstream
public class WebSecurityConfig {

    @Autowired
    private GoogleUserDeatilsServiceImpl googleUserDeatilsService;
=======
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

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
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler);

        http.authorizeRequests()
//                .antMatchers("/user/**").permitAll()
//                .anyRequest().authenticated()
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

        // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
        http.apply(new JwtSecurityConfig(tokenProvider));

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

>>>>>>> Stashed changes

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
