package com.eazybytes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Value("${git.client-id}")
    private String gitClientId;

    @Value("${git.client-secret}")
    private String gitClientSecret;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
     httpSecurity.authorizeHttpRequests((requests)->requests
             .requestMatchers("/secure").authenticated()
             .anyRequest().permitAll())
             .formLogin(Customizer.withDefaults())
             .oauth2Login(Customizer.withDefaults());
     return httpSecurity.build();
  }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration github = githubClientRegistration();
//        ClientRegistration facebook = facebookClientRegistration();

       // return new InMemoryClientRegistrationRepository(github,facebook);
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration githubClientRegistration(){
      return CommonOAuth2Provider.GITHUB.getBuilder("github")
                                        .clientId(gitClientId)
                                        .clientSecret(gitClientSecret).build();
    }



//    private ClientRegistration facebookClientRegistration(){
//        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
//                .clientId("")
//                .clientSecret("").build();
//    }

}
