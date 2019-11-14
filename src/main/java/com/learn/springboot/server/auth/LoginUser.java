package com.learn.springboot.server.auth;

import com.learn.springboot.dataProvider.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User implements Authentication {
    public LoginUser(String username, String password,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private AppUser appUser;
    private boolean authenticated = true;

  public AppUser getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUser appUser) {
    this.appUser = appUser;
    this.authenticated = true;
  }

    @Override
    public Object getCredentials() {
        return appUser.getPassword();
    }

    @Override
    public Object getDetails() {
        return appUser;
    }

    @Override
    public Object getPrincipal() {
        return appUser;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return appUser.getUsername();
    }

    public static class LoginRole implements GrantedAuthority {
        private String roleName;
        public LoginRole() {}
        public LoginRole(String roleName) {
            this.roleName = roleName;
        }
        public String getRoleName() {
            return roleName;
        }
        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }
        @Override
        public String getAuthority() {
            return roleName;
        }
    }
}