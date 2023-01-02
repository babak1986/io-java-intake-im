package com.babak.iojavaintake.user;

import com.babak.iojavaintake.base.BaseEntity;
import com.babak.iojavaintake.userauthority.UserAuthority;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Data
public class User extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private List<UserAuthority> authorities = new ArrayList<>();

    @Transient
    public String defaultRole() {
        return authorities.get(0).getAuthority();
    }
}
