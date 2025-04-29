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
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ไม่ใช้ session
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/auth/login", "/auth/register").permitAll() // ✅ เปลี่ยนตรงนี้
                .requestMatchers("/login").permitAll() // ให้สิทธิ์เข้าถึง /login
                .requestMatchers("/user").hasAnyAuthority("USER", "ADMIN") // ให้สิทธิ์เข้าถึง /user สำหรับ USER หรือ
                                                                           // ADMIN
                .requestMatchers("/admin").hasAuthority("ADMIN") // ให้สิทธิ์เข้าถึง /admin สำหรับ ADMIN เท่านั้น
                .anyRequest().authenticated() // ต้องการ authentication สำหรับ request อื่นๆ
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler) //แสดงการจัดการ Access Denied
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // ใช้
                                                                                                         // JwtAuthenticationFilter
                                                                                                         // ก่อน
                                                                                                         // UsernamePasswordAuthenticationFilter

        return http.build();
    }
}
