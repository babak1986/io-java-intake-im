package com.babak.iojavaintake.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
