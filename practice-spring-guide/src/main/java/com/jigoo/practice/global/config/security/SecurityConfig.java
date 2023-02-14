package com.jigoo.practice.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin((login) -> login
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        /*
                        .loginPage()              // GET  /login
                        .loginProcessingUrl()     // POST /login
                        .failureUrl()             // GET  /login?error
                        */
                )

                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        /*
                        .logoutUrl()              // /logout (CSRF 활성 시 POST만 허용)
                        .logoutSuccessUrl()       // GET /login?logout
                        */
                )

                .authorizeRequests((authReq) -> authReq
                        .mvcMatchers("/admin").hasRole("ADMIN")
                        .antMatchers("/login").anonymous()
                        .anyRequest().permitAll()
                )

                .exceptionHandling((exHandle) -> exHandle
                        .authenticationEntryPoint((req, rep, authEx) -> {
                            if ("XMLHttpRequest".equals(req.getHeader("x-requested-with"))) {
                                rep.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            } else {
                                rep.sendRedirect("/login");
                            }
                        })
                )

        ;

        return http.build();
    }

    @Bean
    @Order(0)
    public SecurityFilterChain resourcesFilterChain(HttpSecurity http) throws Exception {
        http
                .requestMatchers((matchers) -> matchers
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                )
                .authorizeRequests((auth) -> auth
                        .anyRequest().permitAll()
                )
                .requestCache(RequestCacheConfigurer::disable)
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable);

        return http.build();
    }

}