package it.tcgroup.vilear.partner.Service;


import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;

public interface TeacherService {

    /**
     * Aggiunge un Teacher
     * @param teacherModel contiene i dati del Docente
     */
    TeacherModel add(TeacherModel teacherModel);

    /**
     * Recupera un teacher
     * @param personId  id della persona
     * @param partnerId id del partner
     */
    TeacherModel get(Integer personId, Integer partnerId);

    /**
     * Recupera tutti Teacher associati al Partner
     * @param page contiene la pagina da visualizzare
     * @param pageSize contiene la dimensione della pagina
     */
    PaginatedResponse<TeacherModel> getByPartner(Integer partnerId, Integer page, Integer pageSize);

    /**
     * Elimina un Teacher
     *
     * @param partnerId id del Partner
     * @param teacherId id del Docente
     */
    void delete(Integer  teacherId, Integer partnerId);

}
