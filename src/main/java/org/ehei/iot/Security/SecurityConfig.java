package org.ehei.iot.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
httpSecurity
                .authorizeRequests()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/Api").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/SeConnecter")
                .loginProcessingUrl("/auth")
                .defaultSuccessUrl("/TodayWeather", true)
                .permitAll().and().logout(logout ->
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/SeConnecter") // Redirect after logout
                        .permitAll()
        ).csrf().disable();

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authManager(){
        return new ProviderManager(authenticationProvider());
    }



}
