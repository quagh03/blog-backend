package com.example.blogbackend.config;

import com.example.blogbackend.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(requests -> requests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        //ROLE ADMIN
                        .requestMatchers(
                                "/api/blog/users/admin/**",
                                "/api/blog/admin/role").hasAuthority("ROLE_ADMIN")
                        //GET LOGGED-IN USER INFO
                        .requestMatchers("/api/blog/users/info").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/blog/users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/blog/users").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/blog/users/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/blog/users").permitAll()
                        //POSTS
                        .requestMatchers(HttpMethod.POST,"/api/blog/posts/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_AUTHOR")
                        .requestMatchers(HttpMethod.PUT,"/api/blog/posts/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_AUTHOR")
                        .requestMatchers(HttpMethod.POST,"/api/blog/posts/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_AUTHOR")
                        .requestMatchers(HttpMethod.GET,"/api/blog/posts/**").permitAll()
                        //IMAGES
                        .requestMatchers(HttpMethod.POST, "/api/blog/image/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_AUTHOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/blog/image/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_AUTHOR")
                        .requestMatchers(HttpMethod.GET, "/api/blog/image/**").permitAll()
                        //PERMIT ALL
                        .requestMatchers(
                                "/api/blog/users/register",
                                "/api/blog/users/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api/blog/users/{id}").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults());

        return http.build();
    }
}
