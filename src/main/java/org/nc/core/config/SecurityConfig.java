package org.nc.core.config;

import org.nc.core.config.security.AuthManager;
import org.nc.core.config.security.AuthorityService;
import org.nc.core.config.security.SecurityContextRepository;
import org.nc.core.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig{
    private static List<String> clients = Arrays.asList("google", "facebook");

    private final AuthManager authManager;
    private final SecurityContextRepository securityContextRepository;
    private final Environment env;

    public SecurityConfig(AuthManager authManager, SecurityContextRepository securityContextRepository, Environment env) {
        this.authManager = authManager;
        this.securityContextRepository = securityContextRepository;
        this.env = env;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        return http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .oauth2Login().authenticationManager(authManager).and()
                .oauth2Client().authenticationManager(authManager).and()
                .authenticationManager(authManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository(){
        List<ClientRegistration> registrations = clients.stream()
                .map(this::getRegistration)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new InMemoryReactiveClientRegistrationRepository(registrations);
    }

    @Bean
    public AuthorityService userDetailsService(UserRepository userRepository) {
        return new AuthorityService(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private ClientRegistration getRegistration(String client) {
        String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
        String clientId = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("facebook")) {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }

}