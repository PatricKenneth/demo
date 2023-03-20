package br.com.demo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;

@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    Hibernate5Module hibernate5Module = new Hibernate5Module();
    JavaTimeModule module = new JavaTimeModule();

    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    hibernate5Module.configure(Feature.FORCE_LAZY_LOADING, false);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mapper.registerModule(module);
    mapper.registerModule(hibernate5Module);
    return mapper;
  }

}
