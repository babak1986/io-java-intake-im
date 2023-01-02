package com.babak.iojavaintake.jwt;

import com.babak.iojavaintake.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiry}")
    private String expiry;

    public String generateToken(User user) {
        Date issueDate = Calendar.getInstance().getTime();
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("password", user.getPassword())
                .setSubject(user.getId().toString())
                .setIssuedAt(issueDate)
                .setExpiration(new Date(issueDate.getTime() + Long.valueOf(expiry)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsername(String token) {
        return (String) getClaims(token).get("username");
    }

    public String getPassword(String token) {
        return (String) getClaims(token).get("password");
    }

    public boolean validate(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }
}
