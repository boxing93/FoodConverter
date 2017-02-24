package foodconverter.config;

import foodconverter.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Iliya on 14.12.2016 г..
 */
public class AppUserDetails extends User implements UserDetails {

    public AppUserDetails(ArrayList<String> roles, User user) {
        super(user.getEmail(), user.getFullName(), user.getPassword(),user.getRecipes());
        this.roles = roles;
        this.user = user;
    }

    private ArrayList<String> roles;
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public String getUsername() {
        return null;
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
