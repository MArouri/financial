package edu.birzeit.financial.security;

import edu.birzeit.financial.opa.OPAVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().cacheControl();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/health_check","/users").permitAll()
                .anyRequest().authenticated().accessDecisionManager(accessDecisionManager())
                .and()
                .addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        System.out.println("AccessDecisionManager Called .........");
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays
                .asList(new OPAVoter("http://localhost:8181/v1/data/http/authz/allow"));
        return new UnanimousBased(decisionVoters);
    }
}