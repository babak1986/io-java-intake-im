package com.babak.iojavaintake.user;

import com.babak.iojavaintake.jwt.JwtRequest;
import com.babak.iojavaintake.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody JwtRequest jwtRequest) {
        User user = service.login(jwtRequest);
        if (user != null) {
            return new ResponseEntity(jwtUtil.generateToken(user), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
