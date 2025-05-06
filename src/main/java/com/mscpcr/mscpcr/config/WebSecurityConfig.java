package com.mscpcr.mscpcr.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // .requestMatchers("/", "/user/verifyOtp", "/fetchFee/**", "/static/**", "/css/**", "/javascript/**", "/bootstrap/**", "/img/**").permitAll()
                        // .requestMatchers("/audit", "/establishments", "/edit-establishments", "/establishments/edit/{id}", "/create-establishments", "/establishments/show").hasAnyRole("ADMIN", "DCPU", "POLICE", "COURT")
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/admin-dashboard").hasRole("admin")
                        .requestMatchers("/dcpuPage").hasRole("dcpu")
                        .requestMatchers("/policePage").hasRole("police")
                        .requestMatchers("/courtPage").hasRole("court")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Custom login page
                        .loginProcessingUrl("/login") // URL to submit the login form
                        .successHandler(authenticationSuccessHandler()) // Handle successful login
                        .failureUrl("/login?error=true") // Redirect on login failure
                        .usernameParameter("username") // Match the form field name
                        .passwordParameter("password") // Match the form field name
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling((exception) -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                            if (authentication != null) {
                                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                                if (roles.contains("admin")) {
                                    response.sendRedirect("/admin-dashboard");
                                } else if (roles.contains("dcpu")) {
                                    response.sendRedirect("/dcpuPage");
                                } else if (roles.contains("police")) {
                                    response.sendRedirect("/policePage");
                                } else if (roles.contains("court")) {
                                    response.sendRedirect("/courtPage");
                                } else {
                                    response.sendRedirect("/userpage");
                                }
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

                if (authentication != null) {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                    if (roles.contains("admin")) {
                        response.sendRedirect("/admin-dashboard");
                    } else if (roles.contains("dcpu")) {
                        response.sendRedirect("/dcpuPage");
                    } else if (roles.contains("police")) {
                        response.sendRedirect("/policePage");
                    } else if (roles.contains("court")) {
                        response.sendRedirect("/courtPage");
                    } else {
                        response.sendRedirect("/userpage");
                    }
                }
            }
        };
    }
}