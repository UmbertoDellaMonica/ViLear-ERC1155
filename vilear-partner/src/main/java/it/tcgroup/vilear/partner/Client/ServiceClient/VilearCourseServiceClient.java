package it.tcgroup.vilear.partner.Client.ServiceClient;

public interface VilearCourseServiceClient {
    Boolean deleteTools(Integer toolId);
    Boolean deleteTeachers(Integer toolId);
    Boolean deleteLogistics(Integer toolId);
}
