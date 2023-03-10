package it.tcgroup.vilear.registration.Security.Provider;

import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import it.tcgroup.vilear.registration.Client.ServiceClient.VilearAuthServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class VilearRegistrationDetailService implements UserDetailsService {

    @Autowired
    private VilearAuthServiceClient vilearAuthServiceClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String pass = "pass";
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        UserAuthenticationResponse userAuthenticationResponse = vilearAuthServiceClient.verifyTokenByUsername(username);
        List<String> roles = userAuthenticationResponse.getRoles();
        List<SimpleGrantedAuthority>simpleGrantedAuthorityList = new ArrayList<>();
        for(String r: roles){
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(r));
        }
        user = new User(userAuthenticationResponse.getEmail(),passwordEncoder.encode(pass),simpleGrantedAuthorityList);
        return user;
    }
}
