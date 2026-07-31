package uk.co.bhavanasig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CoreWebSecurityConfig {

  /**
   * A minimal security configuration: allows read-only endpoints within Dronetrics and denies all else
   * CSRF remains enabled (default)
   */
  @Bean
  public SecurityFilterChain coreWebSecurityConfigFilterChain(HttpSecurity http) {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/rest/api/drone/**").permitAll()
        .anyRequest().denyAll()
    );

    return http.build();
  }
}
