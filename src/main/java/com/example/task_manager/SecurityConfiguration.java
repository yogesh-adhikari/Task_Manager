package com.example.task_manager;

import com.example.task_manager.services.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    private final UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/task") // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login.html page

                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout") // append a query string value

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeRequests()
                .antMatchers("/", "/users/signup/**","/js/**", "/css/**") // anyone can see the home and the ads pages
                .permitAll()



                /* Pages that require authentication */
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/feed/**",
                        "/userProfile/**",
                        "/tasks/createTask/**",
                        "/createTask/**",
                        "/editTask/**",
                        "/categories/**",
                        "/status/**",
                        "/completeTask/**"

                )
                .authenticated()


                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()

//                *****************************************************************************
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
        ;


        return http.build();
    }


}