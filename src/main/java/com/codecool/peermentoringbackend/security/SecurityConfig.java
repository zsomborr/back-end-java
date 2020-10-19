package com.codecool.peermentoringbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requiresChannel()
                .and()
                .httpBasic().disable()

                .csrf().disable()
                .cors().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/tags/**").authenticated()
                .antMatchers("/tags").authenticated()
                .antMatchers("/search").authenticated()
                .antMatchers("/review").authenticated()
                .antMatchers("/review/**").authenticated()

                .antMatchers("/filter/**").authenticated()
                .antMatchers("/question").authenticated()
                .antMatchers("/question/**").authenticated()
                .antMatchers("/answers").authenticated()
                .antMatchers("/answers/**").authenticated()
              .antMatchers("/auth/authentication").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/reg/registration").permitAll()

                .antMatchers(HttpMethod.OPTIONS, "/reg/registration").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/auth/login").permitAll()
//                .antMatchers("/user-service/user/getUser/**").authenticated()
//                .antMatchers("/notes-service/**").authenticated()


                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);
    }


}
