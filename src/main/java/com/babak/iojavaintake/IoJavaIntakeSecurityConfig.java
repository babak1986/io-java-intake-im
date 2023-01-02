package com.babak.iojavaintake;

import com.babak.iojavaintake.jwt.JwtFilter;
import com.babak.iojavaintake.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class IoJavaIntakeSecurityConfig implements WebMvcConfigurer, AuthenticationEntryPoint {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Value("${cors.origins}")
    private String[] origins;
    @Value("${cors.methods}")
    private String[] methods;
    @Value("${cors.headers}")
    private String[] headers;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        httpSecurity.userDetailsService(userService);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return httpSecurity.build();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorised");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods(methods)
                .allowedHeaders(headers);
    }
}
