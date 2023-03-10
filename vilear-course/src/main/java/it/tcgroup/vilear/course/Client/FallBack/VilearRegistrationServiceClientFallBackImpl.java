package it.tcgroup.vilear.course.Client.FallBack;

import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.base.Payload.Response.RolePersonResponse;
import it.tcgroup.vilear.course.Client.VilearRegistrationClient;
import org.springframework.http.ResponseEntity;

public class VilearRegistrationServiceClientFallBackImpl implements VilearRegistrationClient {
    @Override
    public ResponseEntity<RolePersonResponse> getRolePerson(RoleEnum role, Integer personId) {
        return null;
    }

    @Override
    public ResponseEntity<PersonResponse> getPerson(Integer personId) {
        return null;
    }
}
