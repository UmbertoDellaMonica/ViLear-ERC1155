package it.tcgroup.vilear.partner.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.partner.Client.VilearCourseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearCourseServiceClientImpl implements VilearCourseServiceClient{
    private final VilearCourseClient vilearCourseClient;

    @Override
    public Boolean deleteTools(Integer toolId) {
        try {
            ResponseEntity<Boolean>toolResponseEntity = vilearCourseClient.deleteTools(toolId);
            if(!toolResponseEntity.hasBody()){
                throw new RuntimeException("EXCEPTION ");
            }
            return toolResponseEntity.getBody();
        }catch(FeignException e){
            throw  new RuntimeException("EXCEPTION: "+e.getMessage());
        }
    }

    @Override
    public Boolean deleteTeachers(Integer teacherId) {
        try {
            ResponseEntity<Boolean>teachersResponseEntity = vilearCourseClient.deleteTeachers(teacherId);
            if(!teachersResponseEntity.hasBody()){
                throw new RuntimeException("EXCEPTION ");
            }
            return teachersResponseEntity.getBody();
        }catch(FeignException e){
            throw  new RuntimeException("EXCEPTION: "+e.getMessage());
        }
    }

    @Override
    public Boolean deleteLogistics(Integer logisticId) {
        try {
            ResponseEntity<Boolean>logisticEntityResponse = vilearCourseClient.deleteLogistics(logisticId);
            if(!logisticEntityResponse.hasBody()){
                throw new RuntimeException("EXCEPTION ");
            }
            return logisticEntityResponse.getBody();
        }catch(FeignException e){
            throw  new RuntimeException("EXCEPTION: "+e.getMessage());
        }
    }
}
