package it.tcgroup.vilear.course.Service;

public interface CertificateCourseSCService {

    /**
     * Registra un certificato a livello della Blockchain
     * @param evaluationId
     * @param courseId
     * @param teacherCourseId
     * @param privateKey
     * @return
     */
    Boolean registerCertificato(Integer evaluationId,
                                Integer courseId,
                                Integer teacherCourseId,
                                String privateKey) throws Exception;

    /**
     * Recupera un certificato mediante la sua valutazione relativa al Corso
     *
     * @param evaluationId id della Valutazione
     * @param privateKey   chiave privata che si riferisce all'address dell'Utente
     */
    String getCertificato(Integer evaluationId, String privateKey) throws Exception;
}
