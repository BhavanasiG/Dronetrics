package uk.co.bhavanasig;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class CoreWebConfig {

  /**
   * A {@link RestClient.Builder} pre-configured to (de)serialise JSON with the
   * application-managed {@link JsonMapper}, so consumers inherit settings such as
   * case-insensitive enum parsing instead of a bare default mapper.
   *
   * <p>Prototype-scoped so each injection point gets its own builder to mutate
   * (base URL, headers, etc.) without affecting other consumers.
   */
  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public RestClient.Builder restClientBuilder(JsonMapper jsonMapper) {
    return RestClient.builder()
        .configureMessageConverters(builder -> builder
            .registerDefaults()
            .withJsonConverter(new JacksonJsonHttpMessageConverter(jsonMapper)));
  }
}
