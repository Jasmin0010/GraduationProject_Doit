package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Slf4j
@Configuration
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOriginPattern("http://localhost:3000"); // 변경된 부분
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            return config;
        }))// CORS 설정
            .csrf(csrf -> csrf.disable()) // CSRF 설정 비활성화
            .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic 인증 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 관리 설정

            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                	.requestMatchers(
                			"/h2-console/**"
                			).permitAll()  // Allow all accesses to H2 console
                    .requestMatchers(
                		"/", "/auth/**","/login"
                    		).permitAll()
                    .anyRequest().authenticated()            
            )

            .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
       
        

        return http.build();
    }
}

//
//"/swagger-resources/**",
//"/swagger-ui.html",
//"/swagger-ui/index.html",
//"/v3/api-docs",
//"/webjars/**",
