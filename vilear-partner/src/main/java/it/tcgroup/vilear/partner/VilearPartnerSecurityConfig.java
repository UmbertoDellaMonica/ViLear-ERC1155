package it.tcgroup.vilear.partner;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.partner.Security.Filter.JwtCheckTokenFilter;
import it.tcgroup.vilear.partner.Security.Provider.VilearPartnerDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalAuthentication
public class VilearPartnerSecurityConfig  {

    /**
     * Configura tutte le rotte della mia applicazione,
     * persino i file che possono essere eseguiti
     * all'interno di essa
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Disabilito attacchi di CSRF
        http.csrf().disable();
        // Creo una sessione stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Aggiunge il filtraggio prima e dopo sia con l'autenticazione
        http.addFilterBefore(jwtCheckTokenFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests()
                // set endpoint for AdministratorController
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                // Set End Point for Partner
                .antMatchers(HttpMethod.GET,"/partner").hasAnyRole(RoleEnum.PARTNER.name())
                .antMatchers("external/partner/**").anonymous()
                .antMatchers(HttpMethod.PUT,"/partner").hasAnyRole(RoleEnum.SUPER_ADMIN.name())
                .antMatchers("/admin/partner/**").hasRole(RoleEnum.SUPER_ADMIN.name())
                .antMatchers("/logistic/**").hasAnyRole(
                        RoleEnum.SUPER_PARTNER.name(),
                        RoleEnum.SUPER_ADMIN.name(),
                        RoleEnum.PARTNER.name()
                )
                .antMatchers("/teacher/**").hasAnyRole(
                        RoleEnum.SUPER_PARTNER.name(),
                        RoleEnum.SUPER_ADMIN.name()
                )
                .antMatchers("/tool/**").hasAnyRole(
                        RoleEnum.SUPER_PARTNER.name(),
                        RoleEnum.SUPER_ADMIN.name()
                )
                .and()
                .formLogin().and().logout().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new VilearPartnerDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtCheckTokenFilter jwtCheckTokenFilter(){
        return new JwtCheckTokenFilter();
    }

}
