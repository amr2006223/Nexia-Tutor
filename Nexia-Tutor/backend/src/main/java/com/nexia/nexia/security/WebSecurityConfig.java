package com.nexia.nexia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//class for filters
@Configuration // methods return instance of classes
@EnableWebSecurity // confirguration methods of web security
public class WebSecurityConfig {
    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean // notation => that this function will return an instance of SecurityFilterChain
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests.
                requestMatchers("/api/auth/login").permitAll().
                requestMatchers("/api/**").permitAll().
                requestMatchers("/**").permitAll().
                requestMatchers("/api/auth/**").permitAll().
                requestMatchers("/user/**").permitAll().
                requestMatchers("/admin/**").
                hasAuthority("ADMIN").
                requestMatchers("/test/**").
                hasAuthority("USER").
                anyRequest().
                authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
