package it.tcgroup.vilear.sender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableGlobalAuthentication
@Configuration
public class ViLearSenderConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Disabilito attacchi di CSRF
        http.csrf().disable();
        // Creo una sessione stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // set endpoint for AdministratorController
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                // EndPoint for EmailController
                .antMatchers("/email").permitAll()
                .and()
                .formLogin()
                .and().
                logout()
                .disable();
        return http.build();
    }



}
