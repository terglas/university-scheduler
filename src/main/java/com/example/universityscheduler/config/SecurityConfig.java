package com.example.universityscheduler.config;

import com.example.universityscheduler.config.property.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProperties jwtProperties;
    private final JwtTokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .headers()
                .contentSecurityPolicy(jwtProperties.getContentSecurityPolicy())
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .frameOptions().sameOrigin()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/schedules/extended").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**").authenticated()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().permitAll()
            .and()
            .httpBasic()
            .and()
            .exceptionHandling()
            .and()
            .apply(securityConfigurerAdapter());
        return http.build();
    }

    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(tokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
