package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.ToolCourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface ToolCourseRepository extends JpaRepository<ToolCourseEntity, ToolCourseEntity.PrimaryKey> {

    /**
     * Recupera tutte le date dei corsi
     * scorrendo tra quelle che già esistono e
     * vedendo qual'è la quantità di tool che mi servono per quel giorno
     * Inoltre fa la somma dei campi della quantità che mi serve per quei corsi e se vi saranno altri corsi
     * che richiederanno quell'associazione allora si dovrà verificare la disponibilità in magazzino
     */
    @Query(value="select sum(tc.quantity) as quantity " +
            "            from tools_courses tc ,courses c ,agenda a "+
            "            where tc.course_id = c.id and " +
            "            a.course_id = c.id and " +
            "            tc.tool_id=:toolId  and " +
            "            a.course_date in :dates " +
            "            group by a.course_date " +
            "            order by sum(tc.quantity) desc ", nativeQuery = true)
    List<Integer> getToolCourseDetails(@Param("toolId") Integer toolId, @Param("dates")List<Date> dates);

    /**
     * Recupera tutti i tool associati al Corso
     * @param courseId id del Corso
     * @param pageRequest contiene i dati della Paginazione
     */
    @Query("SELECT t from ToolCourseEntity t where course_id=:courseId ")
    Page<ToolCourseEntity> findByCourse(@Param("courseId") Integer courseId, PageRequest pageRequest);

    @Query("SELECT t from ToolCourseEntity t where tool_id=:toolId ")
    List<ToolCourseEntity> findByTool(@Param("toolId") Integer toolId);
}
