package com.learn.springboot.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.learn.springboot.dataProvider.repo.AppUserRepository;
import com.learn.springboot.server.auth.DbAuthenticationProvider;
import com.learn.springboot.server.auth.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DbAuthenticationProvider dbAuthenticationProvider;
//  @Autowired
//  TokenAuthenticationService jwtAuthTokenService;
    @Autowired
    AppUserRepository userRepo;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(dbAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // filters
        //http.addFilterBefore(new AuthenticationFilter(jwtAuthTokenService,userRepo,apiKsRepo,siteRepo), UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(new ErrorHandlingFilter(), AuthenticationFilter.class);

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v1/**").authenticated();
        http.authorizeRequests().anyRequest().permitAll();

        http.formLogin().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                    if (response.isCommitted()) {
                        return;
                    }
                }

                LoginUser u = (LoginUser) authentication.getPrincipal();
                new DefaultRedirectStrategy().sendRedirect(request, response, "/api/v1/hello");
            }
        }).permitAll();
        http.logout().permitAll();
    }
}
