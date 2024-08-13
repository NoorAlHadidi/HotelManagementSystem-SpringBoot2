package com.brightskies.hotelsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private JWTFilter jwtFilter;
    private UserDetailsService userDetailsService;

    private static final String[] authorizationWhiteList = {
            "/api/user/signup",
            "/api/user/login",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    private static final String[] authorizationAdmin = {
            "/api/room/add",
            "/api/reservation/complete/**",
            "/api/user/delete/**",
    };

    private static final String[] authorizationAdminAndStaff = {
            "/api/room/display",
            "/api/room/display/**",
            "/api/reservation/filer/date",
            "/api/reservation/display",
            "/api/reservation/display/**",
            "/api/user/display",
    };

    private static final String[] authorizationAdminAndCustomer = {
            "/api/reservation/add",
            "/api/reservation/update/**",
            "/api/reservation/cancel/**",
    };

    @Autowired
    public SecurityConfiguration(JWTFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(customizer -> customizer.disable()).
                authorizeHttpRequests(request -> request
                        .requestMatchers(authorizationWhiteList).permitAll()
                        .requestMatchers(authorizationAdmin).hasRole("admin")
                        .requestMatchers(authorizationAdminAndStaff).hasAnyRole("admin", "staff")
                        .requestMatchers(authorizationAdminAndCustomer).hasAnyRole("admin", "customer")
                        .anyRequest().authenticated()).
                httpBasic(Customizer.withDefaults()).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
