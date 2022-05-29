package com.lti.upskill.userservice.config;

import com.lti.upskill.userservice.filter.JwtFilter;
import com.lti.upskill.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;   // this is my custom user service/   (earlier - userService)

    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
       return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/users/helloadmin").hasRole("ADMIN")
                .antMatchers("/users/hellouser").hasAnyRole("USER", "ADMIN")

                //.antMatchers("/hello", "/authenticate", "/register", "/get/{id}", "/get/all", "/login", "/logout", "/delete/{id}")
                .antMatchers("/users/authenticate")

                .permitAll()
                .anyRequest()
                .authenticated()
//                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                //Add a filter to validate the tokens with every request
//                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


                .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and(). sessionManagement()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                //Add a filter to validate the tokens with every request
                 http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

    }
}






