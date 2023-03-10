package it.tcgroup.vilear.course.Client.FallBack;

import it.tcgroup.vilear.base.Payload.Request.ToolRequest;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.course.Client.VilearPartnerClient;
import org.springframework.http.ResponseEntity;

public class VilearPartnerServiceClientFallbackImpl implements VilearPartnerClient {
    @Override
    public ResponseEntity<ToolResponse> edit(Integer partnerId, Integer toolId, ToolRequest toolRequest) {
        return null;
    }

    @Override
    public ResponseEntity<TeacherResponse> getTeacher(Integer partnerId, Integer teacherId) {
        return null;
    }

    @Override
    public ResponseEntity<ToolResponse> getTool(Integer partnerId, Integer toolId) {
        return null;
    }

    @Override
    public ResponseEntity<LogisticResponse> getLogistic(Integer partnerId, Integer logisticId) {
        return null;
    }
}
