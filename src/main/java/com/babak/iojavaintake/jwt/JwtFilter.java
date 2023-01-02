package com.babak.iojavaintake.jwt;

import com.babak.iojavaintake.user.User;
import com.babak.iojavaintake.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    final private JwtUtil jwtUtil;
    final private UserService userService;

    @Value("${jwt.excludes}")
    private String[] excludes;

    private boolean requestIsExcluded(HttpServletRequest request) {
        return Arrays.asList(excludes).stream().anyMatch(exclude -> {
            String uri = request.getRequestURI();
            return exclude.contains("**") ? uri.startsWith(exclude.replace("**", "")) : exclude.equals(uri);
        });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (!requestIsExcluded(request)) {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                if (jwtUtil.validate(token)) {
                    User user = userService.load(jwtUtil.getUsername(token), jwtUtil.getPassword(token));
                    if (user == null) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
