package it.tcgroup.vilear.course.Client.ServiceClient;

import feign.FeignException;
import it.tcgroup.vilear.base.Payload.Exception.TeacherNotFoundException;
import it.tcgroup.vilear.base.Payload.Request.ToolRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.course.Client.VilearPartnerClient;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Model.ToolCourseModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VilearPartnerServiceClientImpl implements VilearPartnerServiceClient{

    private final VilearPartnerClient vilearPartnerClient;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TeacherResponse getTeacher(TeacherCourseModel teacherCourseModel) {
        try {
            Integer partnerId = teacherCourseModel.getPartnerId();
            Integer teacherId = teacherCourseModel.getTeacherId();
            ResponseEntity<TeacherResponse> teacherResponseEntity = vilearPartnerClient.getTeacher(partnerId, teacherId);
            if(!teacherResponseEntity.hasBody()){
                throw new TeacherNotFoundException();
            }
            return teacherResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION : "+e.getMessage());
        }
    }

    @Override
    public ToolResponse getTool(ToolCourseModel toolCourseModel) {
        try {
            Integer toolId = toolCourseModel.getToolId(); // si riferisce all'id del Tool
            Integer partnerId = toolCourseModel.getPartnerId();

            ResponseEntity<ToolResponse> teacherResponseEntity = vilearPartnerClient.getTool(partnerId, toolId);
            if(!teacherResponseEntity.hasBody()){
                throw new TeacherNotFoundException();
            }
            return teacherResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION : "+e.getMessage());
        }
    }

    @Override
    public LogisticResponse getLogistic(LogisticCourseModel logisticCourseModel) {
        try {
            Integer logisticId = logisticCourseModel.getLogisticId();
            Integer partnerId = logisticCourseModel.getPartnerId();
            ResponseEntity<LogisticResponse> logisticResponseEntity = vilearPartnerClient.getLogistic(partnerId, logisticId);
            if(!logisticResponseEntity.hasBody()){
                throw new TeacherNotFoundException();
            }
            return logisticResponseEntity.getBody();
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION : "+e.getMessage());
        }
    }

    @Override
    public void editTool(ToolResponse toolResponse) {
        try {
            ToolRequest toolRequest = mapper.map(toolResponse, ToolRequest.class);
            Integer toolId = toolResponse.getId();
            Integer partnerId = toolResponse.getPartner().getId();
            ResponseEntity<ToolResponse>toolResponseEntity = vilearPartnerClient.edit(partnerId,toolId,toolRequest);
            if(!toolResponseEntity.hasBody()){
                throw new RuntimeException("EXCEPTION : Tool HAS NOT BODY ");
            }
        }catch(FeignException e){
            throw new RuntimeException("EXCEPTION :"+e.getMessage());
        }
    }
}
