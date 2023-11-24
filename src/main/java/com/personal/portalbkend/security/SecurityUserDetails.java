package com.personal.portalbkend.security;

import com.personal.portalbkend.security.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
public class SecurityUserDetails implements UserDetails {



    private String stEmail;
    private String password;
    private Boolean isEnable;
    private List<String> rolls = new ArrayList<>();
//    private List<Auth> Authorities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.stEmail;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }

//    @Setter
//    @Getter
//    public static class Auth implements GrantedAuthority {
//


//
//        public Auth(String name) {
//            this.name = name;
//        }
//
//        private String name;
//
//        @Override
//        public String getAuthority() {
//            return this.name;
//        }
//
//    }
}
