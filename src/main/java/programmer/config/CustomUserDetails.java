package programmer.config;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import programmer.entity.ProfileEntity;
import programmer.enumL.GeneralStatus;
import programmer.enumL.RoleEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String phone;
    private String password;
    private RoleEnum role;
    private GeneralStatus status;

    private String name;
    private String surname;
    private String id;//profile id sini olishda kerak


    public CustomUserDetails(ProfileEntity profile) {
    this.phone = profile.getPhone();
    this.password = profile.getPassword();
    this.role=profile.getRoles();
    this.status=profile.getStatus();

this.id = profile.getId();
this.name = profile.getName();
this.surname = profile.getSurname();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.name()));

        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralStatus.ACTIVE);//UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;//UserDetails.super.isEnabled();  buhda status boradi
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public RoleEnum getRole() {
        return role;
    }
}
