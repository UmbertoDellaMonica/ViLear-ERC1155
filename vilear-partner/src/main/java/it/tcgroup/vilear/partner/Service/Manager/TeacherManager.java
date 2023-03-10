package it.tcgroup.vilear.partner.Service.Manager;

import it.tcgroup.vilear.partner.Entity.TeacherEntity;
import it.tcgroup.vilear.partner.Model.TeacherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TeacherManager {

    /**
     * Aggiunge un Teacher
     * @param teacherModel contiene i dati del Teacher
     */
    TeacherEntity save(TeacherModel teacherModel);

    /**
     * Recupera il singolo Docente
     * @param personId id della Persona
     * @param partnerId id del Partner
     */
    TeacherEntity get(Integer personId, Integer partnerId);

    /**
     * Recupera tutti i Teacher associati al Partner
     * @param partnerId id del Partner
     * @param pageRequest contiene la Paginazione
     */
    Page<TeacherEntity> findByPartner(Integer partnerId, PageRequest pageRequest);

    /**
     * Elimina il Teacher tramite
     * @param teacherId id del Teacher
     * @param partnerId id del Partner
     */
    void delete(Integer teacherId, Integer partnerId);
}
