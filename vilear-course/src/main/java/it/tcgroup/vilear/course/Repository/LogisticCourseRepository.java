package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.LogisticCourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface LogisticCourseRepository extends JpaRepository<LogisticCourseEntity, LogisticCourseEntity.PrimaryKey> {

    /**
     * Recupera tutte le date dei corsi
     * scorrendo tra quelle che già esistono e
     * vedendo qual'è la quantità di tool che mi servono per quel giorno
     * Inoltre fa la somma dei campi della quantità che mi serve per quei corsi e se vi saranno altri corsi
     * che richiederanno quell'associazione allora si dovrà verificare la disponibilità in magazzino
     */
    @Query(value =" select case " +
            " when count(*)>0 then true " +
            " else false end from " +
            " logistics_courses lc, agenda a ,courses c  " +
            " where " +
            " lc.course_id = c.id and " +
            " c.id = a.course_id and " +
            " lc.logistic_id=:logisticId and " +
            " a.course_date in :dates " +
            " group by a.course_date; ", nativeQuery = true)
    Boolean existsLogisticCourse(@Param("logisticId") Integer logisticId,@Param("dates") List<Date> dates);

    /**
     * Recupera la Logistica associata al corso
     * @param courseId id del corso
     * @param pageRequest contiene i dati della Pagina
     */
    @Query("SELECT l from LogisticCourseEntity l where course_id=:courseId")
    Page<LogisticCourseEntity> findByCourse(@Param("courseId") Integer courseId, PageRequest pageRequest);

    @Query("SELECT l from LogisticCourseEntity l where logistic_id=:logisticId")
    List<LogisticCourseEntity>findById(@Param("logisticId")Integer logisticId);
}
