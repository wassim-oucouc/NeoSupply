package org.example.neosupply.config;


import lombok.RequiredArgsConstructor;
import org.example.neosupply.Security.JwtAuthenticationFilter;
import org.example.neosupply.Security.JwtUtil;
import org.example.neosupply.service.impl.CustomUserDetailsService;
import org.example.neosupply.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class  securityConfig{

    private final JwtUtil jwtService;
    private final @Lazy CustomUserDetailsService userService;

    @Bean
    public JwtAuthenticationFilter jwtFilter() {
        return new JwtAuthenticationFilter(jwtService, userService);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui/index.html",
                                        "/swagger-ui.html",
                                        "/webjars/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/products/**").hasAnyRole("CLIENT","WAREHOUSE_MANAGER","ADMIN")
                                .requestMatchers("/api/products/**").hasAnyRole("WAREHOUSE_MANAGER","ADMIN")
                                .requestMatchers("/api/carriers/**").hasAnyRole("WAREHOUSE_MANAGER","ADMIN")
                                .requestMatchers("/api/purchase-orders").hasAnyRole("WAREHOUSE_MANAGER","ADMIN")
                                .requestMatchers("/api/sales-orders").hasAnyRole("WAREHOUSE_MANAGER,ADMIN")
                                .requestMatchers("/api/suppliers").hasAnyRole("ADMIN")
                                .requestMatchers("/api/warehouses").hasAnyRole("ADMIN")

                                .requestMatchers("/api/auth/**").permitAll()

                                .anyRequest().authenticated()

                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
