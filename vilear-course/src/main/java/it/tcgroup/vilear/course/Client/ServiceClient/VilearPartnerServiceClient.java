package it.tcgroup.vilear.course.Client.ServiceClient;

import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.course.Model.LogisticCourseModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import it.tcgroup.vilear.course.Model.ToolCourseModel;

public interface VilearPartnerServiceClient {

    /**
     * Recupera il Teacher tramite l'id del Teacher e l'id del partner
     * @param teacherCourseModel contiene i dati del Teacher
     */
    TeacherResponse getTeacher(TeacherCourseModel teacherCourseModel);

    /**
     * Recupera il Tool tramite l'id del tool e l'id del Partner
     * @param toolCourseModel contiene i dati del Tool
     */
    ToolResponse getTool(ToolCourseModel toolCourseModel);

    /**
     * Recupera la Logistica tramite l'id della Logistica e del Partner
     * @param logisticCourseModel contiene i dati della Logistica
     */
    LogisticResponse getLogistic(LogisticCourseModel logisticCourseModel);

    /**
     * Modifica il tool chiamando il salvataggio del Tool-Partner
     * @param toolResponse contiene tutti i dati salvati
     */
    void editTool(ToolResponse toolResponse);

}
