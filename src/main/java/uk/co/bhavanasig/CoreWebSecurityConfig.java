package uk.co.bhavanasig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CoreWebSecurityConfig {

  /**
   * This needs to be updated to not disable CSRF and have an actual security configuration.
   */
  @Bean
  public SecurityFilterChain coreWebSecurityConfigFilterChain(HttpSecurity http) {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/rest/api/drone/**").permitAll()
            .anyRequest().denyAll()
        );

    return http.build();
  }
}
