package com.project.miniecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.project.miniecommerce.security.jwt.AuthEntryPointJwt;
import com.project.miniecommerce.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    .cors(cors -> cors.disable()) // Atur CORS (jika perlu disesuaikan)
    .csrf(csrf -> csrf.disable()) // Menonaktifkan CSRF
    .exceptionHandling(exceptions -> 
        exceptions.authenticationEntryPoint(unauthorizedHandler) // Atur Entry Point yang tidak terotorisasi
    )
    .sessionManagement(session -> 
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Atur kebijakan sesi stateless
    )
    .authorizeHttpRequests(authz -> 
        authz
            .requestMatchers("/auth/**").permitAll() // Izinkan akses untuk endpoint /auth/**
            .anyRequest().authenticated() // Semua permintaan lainnya harus terotentikasi
    )
    .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Pastikan filter ditambahkan

    
        return http.build();
    }
    

    @Bean
    AuthTokenFilter authTokenFilter (){
        return new AuthTokenFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authentication) throws Exception{
        return authentication.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
     

    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //     http.cors().and().csrf().disable().exceptionHandling().
    //     authenticationEntryPoint(unauthorizedHandler).and().sessionManagement().
    //     sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().requestMatchers("/auth/**").permitAll().requestMatchers("/api/**").permitAll().anyRequest().authenticated();
    //     http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    //     return http.build();
    // }    
}
