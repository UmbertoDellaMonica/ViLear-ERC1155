package it.tcgroup.vilear.auth;

import it.tcgroup.vilear.auth.Security.Filter.*;
import it.tcgroup.vilear.auth.Security.Provider.VilearAuthDetailService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.logging.Logger;

@Configuration
@EnableGlobalAuthentication
public class VilearSecurityConfig extends WebMvcConfigurerAdapter {

    /**
     * Configura tutte le rotte della mia applicazione,
     * persino i file che possono essere eseguiti
     * all'interno di essa
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Disabilito attacchi di CSRF
        http.csrf().disable();
        // Creo una sessione stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Effettuo la Login con la Produzione di un JSON WEB TOKEN
        /*
            -> Login che si occupa di verificare l'autenticazione dell'utente
            -> UsernamePasswordAuthentication prevede di effettuare loadByUsername
            -> AuthFilter si occupa di autenticare l'utente
            -> checkTokenFilter deve eseguire il check sul token
            -> logoutFilter deve eseguire il check su username e password
            -> AuthFilterExternal è per le chiamate esterne e verifica il Token e restituisce le informazioni sull'autenticazione
         */
         http.addFilterBefore(portalFilter(), UsernamePasswordAuthenticationFilter.class);
         http.addFilterAfter(loginFilter(),UsernamePasswordAuthenticationFilter.class);
         http.addFilterAfter(logoutFilter(),LoginFilter.class);
         http.addFilterAfter(authExternalFilter(), LogoutFilter.class);

        http.authorizeRequests()
                // set endpoint for AdministratorController
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                // EndPoint for Login
                .antMatchers("/login").permitAll()
                // EndPoint for Logout
                .antMatchers("/logout").permitAll()
                // EndPoint for Every Call other my Microservice
                .antMatchers("/auth").permitAll()
                // Disabilito il Form automatico di Login e Logout
                .and()
                .formLogin()
                .and().
                logout()
                .disable();

        return http.build();
    }

    @Bean
    public UserDetailsService vilearUserDetailService (){
        return new VilearAuthDetailService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper;
    }

    @Bean
    public Logger logger(){
        return Logger.getGlobal();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PortalFilter portalFilter(){
        return new PortalFilter();
    }

    @Bean
    public LogoutFilter logoutFilter(){
        return new LogoutFilter();
    }

    @Bean
    public LoginFilter loginFilter(){
        return new LoginFilter();
    }

    @Bean
    public AuthExternalFilter authExternalFilter(){
        return new AuthExternalFilter();
    }

}
