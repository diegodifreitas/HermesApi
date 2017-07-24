package br.com.hermes.api.security;

import br.com.hermes.model.entitys.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides a basic implementation of the UserDetails interface
 */
public class CustomUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public CustomUserDetails(User user) {
        this.username = user.getName();
        this.password = user.getPassword();
        this.authorities = translate("USER");

//        if (usuario instanceof Administrador) {
//            this.authorities = translate("ADMIN");
//        } else if (usuario instanceof Consumidor) {
//            this.authorities = translate("USER");
//        }

        
    }

    /**
     * Translates the String to a List<GrantedAuthority>
     *
     * @param role the input of role.
     * @return a list of granted authorities
     */
    private Collection<? extends GrantedAuthority> translate(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String name = role.toUpperCase();
        if (!name.startsWith("ROLE_")) {
            name = "ROLE_" + name;
        }
        authorities.add(new SimpleGrantedAuthority(name));
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

}
