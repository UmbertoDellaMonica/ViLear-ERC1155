package it.tcgroup.vilear.course;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.course.Security.Filter.JwtCheckTokenFilter;
import it.tcgroup.vilear.course.Security.Provider.VilearCourseDetailService;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalAuthentication
public class VilearCourseSecurityConfig {

    /**
     * Configura tutte le rotte della mia applicazione,
     * persino i file che possono essere eseguiti
     * all'interno di essa
     *
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
                .antMatchers("/", "index", "/css/**", "/js/**").permitAll()
                .antMatchers("/admin/course").hasRole(RoleEnum.SUPER_ADMIN.name())
                .antMatchers("/agenda").hasRole(
                        RoleEnum.SUPER_ADMIN.name()
                )
                .antMatchers("/course").hasAnyRole(
                        RoleEnum.SUPER_ADMIN.name(),
                        RoleEnum.SUPER_PARTNER.name()
                )
                .antMatchers("/logistic").hasAnyRole(
                        RoleEnum.SUPER_ADMIN.name(),
                        RoleEnum.SUPER_PARTNER.name()
                )
                .antMatchers("/teacher").hasAnyRole(
                        RoleEnum.SUPER_ADMIN.name(),
                        RoleEnum.SUPER_PARTNER.name()
                )
                .antMatchers("/tool").hasAnyRole(
                        RoleEnum.SUPER_ADMIN.name(),
                        RoleEnum.SUPER_PARTNER.name()
                )
                .antMatchers("/teacher/evaluation").hasRole(RoleEnum.TEACHER.name())
                .antMatchers("/student/evaluation").hasRole(RoleEnum.STUDENT.name())
                .antMatchers("/student").hasRole(RoleEnum.STUDENT.name())
                .and()
                .formLogin().and().logout().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new VilearCourseDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtCheckTokenFilter jwtCheckTokenFilter() {
        return new JwtCheckTokenFilter();
    }
}