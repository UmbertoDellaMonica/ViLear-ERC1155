package it.tcgroup.vilear.partner.Service;


import it.tcgroup.vilear.partner.Model.LogisticModel;
import it.tcgroup.vilear.partner.Model.PartnerModel;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import it.tcgroup.vilear.partner.Model.ToolModel;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import org.springframework.data.domain.Page;

public interface PartnerService {

    /**
     * Aggiunge un Partner
     * @param partnerModel contiene i dati del Partner
     * @return il Partner aggiunto
     */
    PartnerModel add(PartnerModel partnerModel);

    /**Mi restituisce la lista di tutti quegl'insegnanti associati ad un partner
     * @return List TeacherModel*/
    Page<TeacherModel> getTeachers(int page, int pageSize, Integer partnerId);

    /**Mi restituisce la lista di tutti quegli strumenti  associati ad un partner
     * @return List ToolModel*/
    Page<ToolModel> getTools(int page, int pageSize, Integer partnerId);

    /**Mi restituisce la lista di tutte quelle logistiche  associate ad un partner
     * @return List {@link LogisticModel}*/
    Page<LogisticModel> getLogistics(int page, int pageSize, Integer partnerId);

    /**
     * Recupera il Singolo Partner
     * @param partnerId id del Partner
     * @return  un singolo Partner
     */
    PartnerModel get(Integer partnerId);

    /**
     * Recupera il Partner dall'id dell'Utente
     * @param userId id dello User associato al Partner
     */
    PartnerModel getByUser(Integer userId);

    /**
     * Recupera tutti i partner Paginati
     * @param page numero della Pagina
     * @return  un singolo Partner
     */
    PaginatedResponse<PartnerModel> getPartners(Integer page, Integer pageSize);


    /**
     * Accettazione/Rifiuto della posizione di Amministratore per un Partner
     * @param partnerId id dell'utente
     * @param admin Accettazione/Rifiuto
     * @return PartnerModel viene restituito
     */
    PartnerModel setAdministrator(Integer partnerId,Boolean admin);

    /**
     * Aggiorna i dati del Partner
     * @param partnerModifyModel contiene i dati del Partner
     * @param partnerId id del partner
     * @return il Partner aggiornato
     */
    PartnerModel update(PartnerModel partnerModifyModel, Integer partnerId);

    /**
     * Elimina un Partner tramite :
     * @param partnerId id del partner
     */
    void delete(Integer partnerId);

    /**
     * Aggiunge un Docente tramite :
     * @param partnerId contiene dati del Partner
     * @param teacherId contiene dati del Docente
     * @return TeacherModel
     */
    TeacherModel addTeacher(Integer teacherId,Integer partnerId);

    /**
     * Aggiunge un Tool mediante  :
     * @param partnerId id del partner
     * @param toolModel contiene i dati
     * @return ToolModel che viene aggiunto
     */
    ToolModel addTool(Integer partnerId,ToolModel toolModel);

    /**
     * Aggiunge una Logistica  tramite :
     * @param partnerId id del Partner
     * @param logisticModel contiene i dati della Logistica
     * @return Logistica appena aggiunta
     */
    LogisticModel addLogistic(Integer partnerId, LogisticModel logisticModel);

    /**
     * Elimina un Teacher tramite:
     *
     * @param teacherId id del Teacher
     * @param partnerId id del Partner
     */
    void deleteTeacher(Integer teacherId,Integer partnerId);

    /**
     * Elimina un Tool tramite :
     *
     * @param toolId    id del tool
     * @param partnerId id Partner
     */
    void deleteTool(Integer toolId,Integer partnerId);

    /**
     * Elimina una Logistica tramite:
     *
     * @param logisticId id della Logistica
     * @param partnerId  id Partner
     */
    void deleteLogistic(Integer logisticId,Integer partnerId);

}
