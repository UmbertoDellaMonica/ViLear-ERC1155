package it.tcgroup.vilear.course.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.course.Client.VilearRegistrationClient;
import it.tcgroup.vilear.course.Model.StudentPartecipationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VilearRegistrationServiceClientImpl implements VilearRegistrationServiceClient{

    private final VilearRegistrationClient vilearRegistrationClient;

    @Override
    public RolePersonResponse getRolePerson(StudentPartecipationModel studentPartecipationModel) {
        try{
            Integer personId = studentPartecipationModel.getPersonId();
            ResponseEntity<RolePersonResponse> rolePersonResponse = vilearRegistrationClient.getRolePerson(RoleEnum.STUDENT,personId);
            if(!rolePersonResponse.hasBody()){
                throw new RuntimeException("EXCEPTION  ROLE_PERSON NOT FOUND");
            }
            return rolePersonResponse.getBody();

        }catch (FeignException e){
            throw new RuntimeException("EXCEPTION :"+e.getMessage());
        }
    }

    @Override
    public PersonResponse getPerson(Integer personId) {
        try{
            ResponseEntity<PersonResponse>personResponseEntity = vilearRegistrationClient.getPerson(personId);
            if(!personResponseEntity.hasBody()){
                throw new RuntimeException("EXCEPTION PERSON NOT FOUND");
            }
            return personResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION :"+e.getMessage());
        }
    }
}
