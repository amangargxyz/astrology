package com.project.astro.astrology.security;

import com.project.astro.astrology.model.ActiveUserStore;
import com.project.astro.astrology.security.jwt.AuthEntryPointJwt;
import com.project.astro.astrology.security.jwt.AuthTokenFilter;
import com.project.astro.astrology.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/api/otp/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/astrology/user/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/astrology/astrologer/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/astrology/client/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/astrology/query/**")).permitAll()
                  .requestMatchers(new AntPathRequestMatcher("/astrology/reply/**")).permitAll()
                  .anyRequest().authenticated()
        );
    
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
//    corsConfig.addAllowedOrigin("http://localhost:3000"); // Add the origin of your React app
    corsConfig.addAllowedOrigin("http://3.26.8.123:3000"); // Add the origin of your React app
    corsConfig.addAllowedHeader("*"); // You can customize this to allow specific headers
    corsConfig.addAllowedMethod("*"); // You can customize this to allow specific HTTP methods

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsFilter(source);
  }

  @Bean
  public ActiveUserStore activeUserStore(){
    return new ActiveUserStore();
  }
}
