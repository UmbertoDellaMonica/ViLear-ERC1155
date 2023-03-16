package it.tcgroup.vilear.course.Controller;

import it.tcgroup.vilear.course.Service.CertificateCourseSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * Il controller viene impiegato per la gestione dei servizi del Certificato relativo al Corso
 * - Servizi associati allo smart contract deployati sulla blockchain:
 * -> Uno studente deve essere in grado di poter registrare un certificato a fine corso
 * -> Uno studente deve essere in grado di poter visionare il certificato associato a quel corso / Opzionale(produrre una mail per l'invio del Certificato)
 */
@RestController
@RequestMapping("/course/student/certificate")
public class StudentCertificateController {


    @Autowired
    private CertificateCourseSCService certificateCourseSCService;

    /**
     * Registra-Certificato
     * Tale controller deve chiamare il microservizio-certificato e quindi poter registrare il certificato
     * Per poter registrare il certificato, ci devono essere le informazioni del Corso,Utente,Docente
     * @param courseId si riferisce all'id del corso
     * @param evaluationId si riferisce alla valutazione che uno studente ha ricevuto per quel corso
     * @param teacherCourseId si riferisce all'id dell'utente che ha insegnato allo studente e inserito una valutazione
     * @param privateKey si riferisce all'address del wallet sulla blockchain che bisogna utilizzare
     */

    @PostMapping("")
    public ResponseEntity<String> registerCertificato(
            @RequestHeader(name = "evaluation_id")Integer evaluationId,
            @RequestHeader(name = "course_id")Integer courseId,
            @RequestHeader(name = "teacher_course_id")Integer teacherCourseId,
            @RequestHeader(name = "private_key")String privateKey
    ) throws Exception {
      if(certificateCourseSCService.registerCertificato(evaluationId,courseId,teacherCourseId,privateKey)) {
          return new ResponseEntity<>("Ok! Certificato Creato.", HttpStatus.OK);
      }else{
          return new ResponseEntity<>("Ok! Certificato Non Creato.", HttpStatus.NOT_FOUND);
      }
    }

    @GetMapping("")
    public ResponseEntity<String> getCertificato(
            @RequestHeader(name = "evaluation_id")Integer evaluationId,
            @RequestHeader(name = "private_key")String privateKey
    ) throws Exception {
        String certificato = certificateCourseSCService.getCertificato(evaluationId,privateKey);
        if(certificato.equalsIgnoreCase("")==false) {
            return new ResponseEntity<>("Dati restituiti: "+certificato, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Ok! Certificato Non Ottenuto.", HttpStatus.NOT_FOUND);
        }
    }

}
