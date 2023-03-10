package it.tcgroup.vilear.course.Repository;

import it.tcgroup.vilear.course.Entity.StudentPartecipationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudentPartecipationRepository extends JpaRepository<StudentPartecipationEntity,StudentPartecipationEntity.PrimaryKey> {

    /**
     * * Recupera la Partecipazione tramite l'id del corso
     * @param courseId id del corso
     * @param pageRequest contiene la Paginazione e le info
     */
    @Query("SELECT s from StudentPartecipationEntity s where course_id=:courseId")
    Page<StudentPartecipationEntity> findByCourse(Integer courseId, PageRequest pageRequest);

}
