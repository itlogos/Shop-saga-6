package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;


@Configuration
public class JwtAuthoritiesConfig {
  @Bean
  public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    JwtAuthenticationConverter standard = new JwtAuthenticationConverter();
    standard.setJwtGrantedAuthoritiesConverter(jwt -> {
      JwtGrantedAuthoritiesConverter scope = new JwtGrantedAuthoritiesConverter();
      Collection<GrantedAuthority> base = scope.convert(jwt);

      Map<String, Object> realm = jwt.getClaim("realm_access");
      List<String> roles = realm != null
              ? (List<String>) realm.getOrDefault("roles", List.of())
              : List.of();

      List<GrantedAuthority> roleAuth = roles.stream()
              .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      base.addAll(roleAuth);
      return base;
    });

    return new ReactiveJwtAuthenticationConverterAdapter(standard);
  }

}
