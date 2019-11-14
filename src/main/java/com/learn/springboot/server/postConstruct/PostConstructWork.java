package com.learn.springboot.server.postConstruct;

import com.learn.springboot.dataProvider.model.AppUser;
import com.learn.springboot.dataProvider.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PostConstructWork {

    @Autowired
    AppUserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void doWork() {
        // seed (if not already) an initial user so we can login
        AppUser existingSeedUser = userRepo.findByUsername("seedUser");
        if (existingSeedUser==null) {
            AppUser u = new AppUser();
            u.setUsername( "seedUser" );
            u.setPassword( encoder.encode("seedPassword") );
            u.setActivities(new AppUser.AppUserActivities());
            userRepo.save(u);
        }
    }
}
