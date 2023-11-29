package com.javatechie.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //Used as Authenticate in 2.7 // configure in
    @Bean
    public UserDetailsService userDetailsService(){
//        // based on the In memory Authentication
//        UserDetails prakash= User.withUsername("prakash")
//                .password(passwordEncoder().encode("prakash"))
//                .roles("USER")
//                .build();
//        UserDetails admin= User.withUsername("John")
//                .password(passwordEncoder().encode("pwd1"))
//                .roles("ADMIN")         // .roles("ADMIN","HR","MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(prakash,admin);
        return new UserInfoDetailsService();
    }

    // Configure method as per methods /mapping // authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(authorise -> authorise.anyRequest().authenticated())
////                .formLogin(Customizer.withDefaults()); // Form based Login Authentication
//                .httpBasic(Customizer.withDefaults()); // http Basic Login Authentication
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/products/welcome", "products/new").permitAll())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/products/**").authenticated())
//                   .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userDetailsService());
         authenticationProvider.setPasswordEncoder(passwordEncoder());
         return authenticationProvider();
     }

}
