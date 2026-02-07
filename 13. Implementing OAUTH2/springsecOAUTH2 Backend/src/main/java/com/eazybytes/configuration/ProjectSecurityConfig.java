package com.eazybytes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

public class ProjectSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
     httpSecurity.authorizeHttpRequests((requests)->requests
             .requestMatchers("/secure").authenticated()
             .anyRequest().permitAll())
             .formLogin(Customizer.withDefaults())
             .oauth2Client(Customizer.withDefaults());
     return httpSecurity.build();
  }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration github = githubClientRegistration();
        ClientRegistration facebook = facebookClientRegistration();

        return new InMemoryClientRegistrationRepository(github,facebook);
    }

    private ClientRegistration githubClientRegistration(){
      return CommonOAuth2Provider.GITHUB.getBuilder("github")
                                        .clientId("")
                                        .clientSecret("").build();
    }

    private ClientRegistration facebookClientRegistration(){
        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId("")
                .clientSecret("").build();
    }





}
