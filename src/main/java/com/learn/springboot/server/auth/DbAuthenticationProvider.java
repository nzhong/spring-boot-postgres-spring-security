package com.learn.springboot.server.auth;

import com.learn.springboot.dataProvider.model.AppUser;
import com.learn.springboot.dataProvider.repo.AppUserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DbAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private AppUserRepository userRepo;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        String password = (String) usernamePasswordAuthenticationToken.getCredentials();
        AppUser appUser = userRepo.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("Invalid Login Username");
        }

        if ( !BCrypt.checkpw(password, appUser.getPassword()) ) {
            //logger.warn("Username {} password {}: invalid password", username, password);
            throw new BadCredentialsException("Invalid Login Password");
        }

        // record this latest login
        AppUser.AppUserActivities activities = appUser.getActivities();
        if (activities==null) {
            activities = new AppUser.AppUserActivities();
        }
        activities.addLogin();
        appUser.setActivities(activities);
        userRepo.save(appUser);

        List<GrantedAuthority> authorities = Arrays.asList( new LoginUser.LoginRole("USER") );
        LoginUser loginUser = new LoginUser(username, password, authorities);
        loginUser.setAppUser(appUser);
        return loginUser;
    }
}
