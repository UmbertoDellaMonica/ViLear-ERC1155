package it.tcgroup.vilear.course.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.base.Payload.Response.UserAuthenticationResponse;
import it.tcgroup.vilear.course.Client.VilearAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearAuthServiceClientImpl implements VilearAuthServiceClient{

    private final VilearAuthClient vilearAuthClient;

    @Override
    public UserAuthenticationResponse verifyToken(String token) {
        try{
            ResponseEntity<UserAuthenticationResponse>booleanResponseEntity = vilearAuthClient.verifyToken(token);
            if(
                    !booleanResponseEntity.getStatusCode().equals(HttpStatus.OK) ||
                    !booleanResponseEntity.hasBody()
            ){
                throw new RuntimeException("NOT VALID TOKEN");
            }
            return booleanResponseEntity.getBody();
        }catch (FeignException e){
            throw new RuntimeException(""+e.getMessage());
        }
    }

    @Override
    public UserAuthenticationResponse verifyTokenByUsername(String username) {
        try{
            ResponseEntity<UserAuthenticationResponse>booleanResponseEntity = vilearAuthClient.verifyTokenByUser(username);
            if(
                    !booleanResponseEntity.getStatusCode().equals(HttpStatus.OK) ||
                            !booleanResponseEntity.hasBody()
            ){
                throw new RuntimeException("NOT VALID TOKEN");
            }
            return booleanResponseEntity.getBody();
        }catch (FeignException e){
            throw new RuntimeException(""+e.getMessage());
        }
    }
}
