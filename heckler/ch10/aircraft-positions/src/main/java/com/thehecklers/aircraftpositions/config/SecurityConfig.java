package com.thehecklers.aircraftpositions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Uncomment this and spring-security in pom.xml if you want to use InMemoryUserDetailsManager instead of OAuth2
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//            .username("ruslan")
//            .password(passwordEncoder().encode("pass"))
//            .roles("USER")
//            .build();
//
//        UserDetails adminUser = User.builder()
//            .username("admin")
//            .password(passwordEncoder().encode("admin"))
//            .roles("USER", "ADMIN")
//            .build();
//
//        System.out.println("My password: " + user.getPassword());
//
//        return new InMemoryUserDetailsManager(user, adminUser);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/aircraftadmin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            //.formLogin(withDefaults())
            .httpBasic(withDefaults());

        return http.build();
    }

    /**
     * OAuth2 Webclient config
     * @param clientRegistrationRepository - list of OAuth2 clients specified for use by the app, usually in application.properties
     * @param authorizedClientRepository - list of OAuth2 clients that represent authenticated user and manage that user's OAuth2AccessToken
     * @return - OAuth2-configured WebClient
     */
    @Bean
    public WebClient client(ClientRegistrationRepository clientRegistrationRepository,
                            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction filter =
            new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                clientRegistrationRepository, authorizedClientRepository);

        filter.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder()
            .baseUrl("http://localhost:7634/")
            .apply(filter.oauth2Configuration())
            .build();
    }

}
