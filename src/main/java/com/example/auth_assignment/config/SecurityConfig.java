package com.example.auth_assignment.config;

import com.example.auth_assignment.infrastructure.security.JwtTokenProvider;
import com.example.auth_assignment.infrastructure.security.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    // Bean สำหรับ JwtAuthenticationFilter
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }

    // กำหนดการตั้งค่าความปลอดภัยต่างๆ
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // เปิด CORS
            .and()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ไม่ใช้ session
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/auth/login", "/auth/register").permitAll() // ให้สิทธิ์ /auth/login และ /auth/register
            .requestMatchers("/user","/data/view","/data/create").hasAnyAuthority("USER", "ADMIN") // USER หรือ ADMIN
            .requestMatchers("/admin","/data/edit/**","/data/delete/**").hasAuthority("ADMIN") // ADMIN เท่านั้น
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // กำหนด CORS Configuration Source
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // อนุญาต origin ของ frontend (เปลี่ยนเป็น URL frontend ของคุณ)
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // หรือถ้าจะให้อนุญาตทุก origin (ไม่แนะนำใน production)
        // configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
