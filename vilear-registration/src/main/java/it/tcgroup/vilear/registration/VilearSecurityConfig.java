package it.tcgroup.vilear.registration;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.registration.Security.Filter.JwtCheckTokenFilter;
import it.tcgroup.vilear.registration.Security.Provider.VilearRegistrationDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalAuthentication
public class VilearSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configura tutte le rotte della mia applicazione,
     * persino i file che possono essere eseguiti
     * all'interno di essa
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disabilito attacchi di CSRF
        http.csrf().disable();
        // Creo una sessione stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Aggiunge il filtraggio prima e dopo sia con l'autenticazione
        http.addFilterBefore(jwtCheckTokenFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests()
                // set endpoint for AdministratorController
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                .antMatchers("/admin/**").hasRole(RoleEnum.SUPER_ADMIN.name())
                // Set End Point for External Call
                .antMatchers("/external/**").anonymous()
                // set end Point for Person Controller
                // Test this route
                .antMatchers("/user/person").hasAnyRole(
                        RoleEnum.STUDENT.name(),
                        RoleEnum.TEACHER.name()
                )
                // set end Point for Registration Controller
                .antMatchers("/user/registration").anonymous()
                // set end Point for UserController
                .antMatchers("/user").permitAll()
                //  set end Point for UserInvitation
                .antMatchers(HttpMethod.POST,"/user/invitation").hasAnyRole(RoleEnum.SUPER_ADMIN.name())
                .antMatchers(HttpMethod.GET,"/user/invitation/{invitation_id}").hasAnyRole(
                        RoleEnum.STUDENT.name(),
                        RoleEnum.TEACHER.name()
                )
                .antMatchers("/user/role/**").hasRole(RoleEnum.SUPER_ADMIN.name())
                .and()
                .formLogin().disable();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new VilearRegistrationDetailService();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtCheckTokenFilter jwtCheckTokenFilter(){
        return new JwtCheckTokenFilter();
    }




}
