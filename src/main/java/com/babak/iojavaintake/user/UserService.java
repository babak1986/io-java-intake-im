package com.babak.iojavaintake.user;

import com.babak.iojavaintake.base.BaseService;
import com.babak.iojavaintake.excption.UsernameExistsException;
import com.babak.iojavaintake.jwt.JwtRequest;
import com.babak.iojavaintake.userauthority.UserAuthority;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User> implements UserDetailsService, PasswordEncoder {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    @Cacheable(cacheNames = "user", key = "#username")
    public User load(String username) {
        return (User) loadUserByUsername(username);
    }

    public User load(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    @Transactional
    public User create(String username, String password, String role) throws UsernameExistsException {
        if (load(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encode(password));
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            UserAuthority userAuthority = new UserAuthority();
            userAuthority.setAuthority(role);
            userAuthority.setUser(user);
            user.getAuthorities().add(userAuthority);
            return repository.save(user);
        }
        throw new UsernameExistsException();
    }

    public User login(JwtRequest userJwkToken) {
        User user = load(userJwkToken.getUsername());
        if (user != null && user.getPassword().equals(encode(userJwkToken.getPassword()))) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return user;
    }

    @SneakyThrows
    @Override
    public String encode(CharSequence password) {
        return new String(MessageDigest.getInstance("SHA-256").digest(password.toString().getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
