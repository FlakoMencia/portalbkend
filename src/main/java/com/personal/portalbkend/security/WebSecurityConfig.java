package com.personal.portalbkend.security;


import com.personal.portalbkend.security.jwt.JwtAuthenticationEntryPoint;
import com.personal.portalbkend.security.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.personal.portalbkend.security.enums.Role.*;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private static final String[] URL_PERMIT_ALL ={"/api/authenticate", "/api/isAlive", "/api/user/register"};


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                                 JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(URL_PERMIT_ALL).permitAll()
                        .requestMatchers("/api/user/activate").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/user/disable").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/user/list").hasAnyRole(ADMIN.name(), USER.name())
                        .requestMatchers("/api/user/find").hasAnyRole(ADMIN.name(), USER.name())
                        .anyRequest().authenticated())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedPage("/errors/access-denied")
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
