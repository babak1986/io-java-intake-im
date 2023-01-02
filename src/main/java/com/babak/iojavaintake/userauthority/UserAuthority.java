package com.babak.iojavaintake.userauthority;

import com.babak.iojavaintake.base.BaseEntity;
import com.babak.iojavaintake.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "tbl_user_authority")
@Data
public class UserAuthority extends BaseEntity implements GrantedAuthority {

    private String authority;
    @ManyToOne
    private User user;
}
