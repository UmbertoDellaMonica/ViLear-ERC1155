package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Response.PersonResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearRegistrationServiceClient;
import it.tcgroup.vilear.course.Model.CertificateCourse;
import it.tcgroup.vilear.course.Model.CourseModel;
import it.tcgroup.vilear.course.Model.StudentEvaluationModel;
import it.tcgroup.vilear.course.Model.TeacherCourseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Service
public class CertificateCourseSCServiceImpl implements CertificateCourseSCService {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private VilearRegistrationServiceClient vilearRegistrationServiceClient;

    @Override
    public Boolean registerCertificato(Integer evaluationId, Integer courseId, Integer teacherCourseId, String privateKey) throws Exception {
        // Creo la Certificazione per il Corso
        CertificateCourse certificateCourse = create(evaluationId,courseId,teacherCourseId);
        // Recupero le credenziali dalla BlockChain
        Credentials credentialsUser = blockchainService.initCredentials(privateKey);
        // Verifico se lo Smart Contract che sia inizializzato oppure no
        if(blockchainService.checkSmartContract()){
            // è inizializzato allora
            return blockchainService.registerCertificato(BigInteger.valueOf(evaluationId), certificateCourse);
        }else{
            // Non è inizializzato
            // Lo inizializzo
            blockchainService.initSmartContractCertificatoCorsoViLear(credentialsUser);
            // Eseguo la registrazione()
            return blockchainService.registerCertificato(BigInteger.valueOf(evaluationId), certificateCourse);
        }
    }

    @Override
    public String getCertificato(Integer evaluationId, String privateKey) throws Exception {
        // Recupera le informazioni dell'utente
        Credentials credentialsUser = blockchainService.initCredentials(privateKey);
        if(blockchainService.checkSmartContract()){
            // è inizializzato allora
            return blockchainService.getCertificato(evaluationId);
        }else{
            // Non è inizializzato
            // Lo inizializzo
            blockchainService.initSmartContractCertificatoCorsoViLear(credentialsUser);
            // Eseguo la registrazione()
            return blockchainService.getCertificato(evaluationId);
        }
    }
    private CertificateCourse create(Integer evaluationId, Integer courseId, Integer teacherCourseId){
        // Prendo la valutazione dello studente insieme ai suoi dati
        StudentEvaluationModel studentEvaluationModel = evaluationService.get(evaluationId,courseId);
        PersonResponse studentResponse = vilearRegistrationServiceClient.getPerson(studentEvaluationModel.getStudentPartecipation().getPersonId());
        String infoUtente =""+studentResponse.getFirstname()+" "+studentResponse.getLastname();

        // Recupero le informazioni sul corso
        CourseModel courseModel = courseService.get(courseId);
        // Recupero le informazioni sul Docente
        TeacherCourseModel teacherCourseModel = teacherCourseService.get(teacherCourseId,courseId);
        PersonResponse teacherResponse = vilearRegistrationServiceClient.getPerson(teacherCourseModel.getTeacherId());
        String infoTeacher =""+teacherResponse.getFirstname()+" "+teacherResponse.getLastname();
        // Creo il Certificato da Inserire in Blockchain
        return new CertificateCourse(courseModel.getName(),studentEvaluationModel.getCreatedAt().toString(),infoUtente,infoTeacher);
    }
}
