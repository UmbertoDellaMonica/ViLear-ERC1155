package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Enum.RoleEnum;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.*;
import it.tcgroup.vilear.base.Payload.Response.LogisticResponse;
import it.tcgroup.vilear.base.Payload.Response.TeacherResponse;
import it.tcgroup.vilear.base.Payload.Response.ToolResponse;
import it.tcgroup.vilear.course.Client.ServiceClient.VilearPartnerServiceClient;
import it.tcgroup.vilear.course.Entity.CourseEntity;
import it.tcgroup.vilear.course.Model.*;
import it.tcgroup.vilear.course.Service.Manager.CourseManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static it.tcgroup.vilear.base.Payload.Enum.StatusEnum.APPROVED;

@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    private ModelMapper mapper;

    @Autowired
    private VilearPartnerServiceClient vilearPartnerServiceClient;

    @Autowired
    private AgendaService agendaService;


    @Autowired
    private TeacherCourseService teacherCourseService;


    @Autowired
    private ToolCourseService toolCourseService;


    @Autowired
    private LogisticCourseService logisticCourseService;

    @Autowired
    private CourseManager courseManager;


    /*
        Verifica se lo stato del Corso sia già APPROVATO/RIFIUTATO
        ---Lancia un'eccezione se vi è in uno di quei Stati
         */
    private void checkCourseStatus(CourseModel courseModel) {
        switch (courseModel.getStatus()) {
            case REJECTED -> throw new CourseAlreadyExistsException();
            case APPROVED -> throw new CourseAlreadyExistsException();
        }
    }

    /*
        Verifica se lo stato della Logistica sia già APPROVATO/RIFIUTATO
        ---Lancia un'eccezione se vi è in uno di quei Stati
         */
    private void checkLogisticCourseStatus(LogisticCourseModel logisticCourseModel) {
        switch (logisticCourseModel.getStatus()) {
            case APPROVED -> throw new CourseAlreadyExistsException();
            case REJECTED -> throw new CourseAlreadyExistsException();
        }
    }

    /*
        Verifica se lo stato del Tool sia già APPROVATO/RIFIUTATO
        ---Lancia un'eccezione se vi è in uno di quei Stati
         */
    private void checkToolCourseStatus(ToolCourseModel toolCourseModel) {
        switch (toolCourseModel.getStatus()) {
            case APPROVED -> throw new CourseAlreadyExistsException();
            case REJECTED -> throw new CourseAlreadyExistsException();
        }
    }

    /*
        Verifica se lo stato del Teacher sia già APPROVATO/RIFIUTATO
        ---Lancia un'eccezione se vi è in uno di quei Stati
     */
    private void checkTeacherCourseStatus(TeacherCourseModel teacherCourseModel) {
        switch (teacherCourseModel.getStatus()) {
            case APPROVED -> throw new CourseAlreadyExistsException();
            case REJECTED -> throw new CourseAlreadyExistsException();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CourseModel add(CourseModel courseModel) {
        // Setta i dati di deafault di un corso
        // Verifica se l'utente che sta aggiungendo il Corso è un admin
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) auth.getAuthorities();
        Boolean tag = Boolean.FALSE;
        for(GrantedAuthority grantedAuthority: grantedAuthorityList){
           if(grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")){
             tag = Boolean.TRUE;
            }
        }
        if(tag){
            courseModel.setActive(true);
            courseModel.setStatus(APPROVED);
        }else{
            courseModel.setActive(false);
            courseModel.setStatus(StatusEnum.PENDING);
        }
        courseModel.setDatePublish(new Date(System.currentTimeMillis()));
        // Salva i dati del Corso
        CourseEntity courseEntity = courseManager.save(courseModel);

        return mapper.map(courseEntity, CourseModel.class);
    }

    @Override
    public CourseModel get(Integer courseId) {
        CourseEntity courseEntity = courseManager.get(courseId);
        CourseModel courseModel = mapper.map(courseEntity, CourseModel.class);

        return courseModel;
    }

    @Override
    @Transactional
    public CourseModel update(CourseModel courseModifyModel, Integer courseId) {
        /*
            Verifica se esiste il Corso :
            -----Se esiste lo Aggiorna,
            ----------Altrimenti lancia un'eccezione
         */
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CourseModel courseModel = get(courseId);
        courseModifyModel.setId(courseId);
        if(timestamp.after(courseModel.getEndDate())){
            courseModifyModel.setActive(false);
        }

        // Aggiorna i dati del Corso
        CourseEntity courseEntity = courseManager.save(courseModifyModel);

        return mapper.map(courseEntity, CourseModel.class);
    }

    @Override
    @Transactional
    public void delete(Integer courseId) {
        courseManager.delete(courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CourseModel setStatusCourse(Integer courseId, DecisionEnum decision) {

        // Verifica che il corso esista
        CourseModel courseModel = get(courseId);

        // Verifica lo stato del Corso
        checkCourseStatus(courseModel);
        /*
            Verifica se ha il Corso è stato ACCETTATO/RIFIUTATO:
            -----ACCETTATO : modifica i dati del corso :
                 -Status, Attivazione del Corso
            ----RIFIUTATO : modifica i dati del corso :
                  -Status
         */
        switch (decision) {
            case ACCEPT: {
                courseModel.setStatus(StatusEnum.APPROVED);
                courseModel.setActive(Boolean.TRUE);
                break;
            }
            case DENY: {
                courseModel.setStatus(StatusEnum.REJECTED);
                break;
            }
        }

        // Aggiorna il corso 
        return update(courseModel, courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LogisticCourseModel setStatusLogisticCourse(Integer courseId, Integer logisticId, DecisionEnum decision) {

        //Verifico se quella logistica è presente
        LogisticCourseModel logisticCourseModel = logisticCourseService.get(courseId, logisticId);
        if (logisticCourseModel == null) {
            throw new LogisticCourseNotFoundException();
        }

        //Verifca se lo stato della logistica è stato già ACCETTATO o RIFIUTATO: lancia un'eccezione
        checkLogisticCourseStatus(logisticCourseModel);
        /*
            Verifica se la Logistica deve essere ACCETTATA oppure RIFIUTATA:
            ----ACCETTATA: ""
            ----RIFIUTATA:  ""
             modifica lo stato della Logistica
             Aggiorna la Logistica
         */

        switch (decision) {
            case ACCEPT: {
                logisticCourseModel.setStatus(StatusEnum.APPROVED);
                break;
            }
            case DENY: {
                logisticCourseModel.setStatus(StatusEnum.REJECTED);
                break;
            }
        }
        // Aggiorno la Logistica associata a quel Corso
        return logisticCourseService.update(logisticCourseModel, logisticId, courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolCourseModel setStatusToolCourse(Integer courseId, Integer toolId, DecisionEnum decision) {
        // Verifica che esista il ToolCourse
        ToolCourseModel toolCourseModel = toolCourseService.get(courseId, toolId);
        if (toolCourseModel == null) {
            throw new ToolCourseNotFoundException();
        }

        // Verifica che lo stato del Tool non sia già Accettato o Rifiutato
        checkToolCourseStatus(toolCourseModel);

        switch (decision) {
            case ACCEPT: {
                toolCourseModel.setStatus(StatusEnum.APPROVED);
                break;
            }
            case DENY: {
                toolCourseModel.setStatus(StatusEnum.REJECTED);
                break;
            }
        }

        // Aggiorna il Tool associato al corso
        return toolCourseService.update(toolCourseModel, toolId, courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TeacherCourseModel setStatusTeacherCourse(Integer courseId, Integer teacherCourseId, DecisionEnum decision) {
        // Verifica che il teacher associato al corso esista
        TeacherCourseModel teacherCourseModel = teacherCourseService.get(teacherCourseId, courseId);

        // Verifica che lo status del Corso sia già Approvato oppure Rifiutato
        checkTeacherCourseStatus(teacherCourseModel);

        switch (decision) {
            case ACCEPT: {
                teacherCourseModel.setStatus(StatusEnum.APPROVED);
                teacherCourseModel.setTeacherId(teacherCourseId);
                break;
            }
            case DENY: {
                teacherCourseModel.setStatus(StatusEnum.REJECTED);
                teacherCourseModel.setTeacherId(teacherCourseId);
                break;
            }
        }
        // Aggiorna il Teacher associato al Corso
        return teacherCourseService.update(
                teacherCourseModel,
                teacherCourseModel.getTeacherId(),
                teacherCourseModel.getCourse().getId()
        );
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AgendaModel addAgenda(Integer courseId, AgendaModel agendaModel) {
        // Verifica che il corso esista
        CourseModel courseModel = get(courseId);

        //Imposto i dati dell'agenda
        agendaModel.setCourse(courseModel);

        // Salva i dati dell'agenda
        return agendaService.add(agendaModel);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TeacherCourseModel addTeacher(TeacherCourseModel teacherCourseModel) {
       CourseModel courseModel = get(teacherCourseModel.getCourse().getId());

        // Recupero i dati del Teacher
        TeacherResponse teacherResponse = vilearPartnerServiceClient.getTeacher(teacherCourseModel);

        teacherCourseModel.setTeacherId(teacherResponse.getId());
        teacherCourseModel.setCourse(courseModel);

        // Verifica l'agenda
        checkAgendaExists(teacherCourseModel.getCourse());
        // Verifica se il corso è attivo e approvato
        checkCourseActive(teacherCourseModel.getCourse());

        List<Date>agendaDays=courseModel.getAgendaList().stream().map(
                agendaModel -> agendaModel.getCourseDate()
        ).collect(Collectors.toList());

        if(teacherCourseService.exists(teacherResponse.getId(),agendaDays)){
            throw new TeacherCourseAlreadyExistsException();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) auth.getAuthorities();
        Boolean tag = Boolean.FALSE;
        for(GrantedAuthority grantedAuthority: grantedAuthorityList){
            if(grantedAuthority.getAuthority().equalsIgnoreCase(RoleEnum.SUPER_ADMIN.getRole())){
                tag = Boolean.TRUE;
            }
        }
        if(tag){
            teacherCourseModel.setStatus(APPROVED);
        }else{
            teacherCourseModel.setStatus(StatusEnum.PENDING);
        }
        return teacherCourseService.add(teacherCourseModel);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ToolCourseModel addTool(ToolCourseModel toolCourseModel) {
        // Recupero il Corso
        CourseModel courseModel = this.get(toolCourseModel.getCourse().getId());
        // Recupero il Tool
        ToolResponse toolResponse = vilearPartnerServiceClient.getTool(toolCourseModel);

        toolCourseModel.setCourse(courseModel);

        // Verifica che il Corso sia approvato e attivo
        checkCourseActive(toolCourseModel.getCourse());

        // Verifica che l'agenda esista
        checkAgendaExists( toolCourseModel.getCourse() );

        // Recupera le date del Corso
        List<Date> agendaDays = courseModel.getAgendaList()
                .stream()
                .map(agendaModel -> agendaModel.getCourseDate()).collect(Collectors.toList());

        List<Integer> summaryQuantityList = toolCourseService
                .getSummaryQuantity(toolResponse.getId(), agendaDays);
        if(!summaryQuantityList.isEmpty()){
            if ((summaryQuantityList.get(0) + toolCourseModel.getQuantity()) > toolResponse.getAvailability()) {
                throw new QuantityException();
            }
        }
        ToolCourseModel toolCourseFound = toolCourseService.get(courseModel.getId(),toolResponse.getId());
        if(toolCourseFound!=null){
            return toolCourseFound;
        }
        // Imposto i dati del Tool
        // Modifico la disponibilità nel Tool
        toolResponse.setAvailability(
                toolResponse.getAvailability()-toolCourseModel.getQuantity()
        );
        vilearPartnerServiceClient.editTool(toolResponse);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) auth.getAuthorities();
        Boolean tag = Boolean.FALSE;
        for(GrantedAuthority grantedAuthority: grantedAuthorityList){
            if(grantedAuthority.getAuthority().equalsIgnoreCase(RoleEnum.SUPER_ADMIN.getRole())){
                tag = Boolean.TRUE;
            }
        }
        if(tag){
            toolCourseModel.setStatus(APPROVED);
        }else{
            toolCourseModel.setStatus(StatusEnum.PENDING);
        }


        toolCourseModel.setStatus(StatusEnum.PENDING);
        toolCourseModel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return toolCourseService.add(toolCourseModel);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LogisticCourseModel addLogistic(LogisticCourseModel logisticCourseModel) {

        CourseModel courseModel = get(logisticCourseModel.getCourse().getId());
        // Call at Vilear Partner
        LogisticResponse logisticResponse = vilearPartnerServiceClient.getLogistic(logisticCourseModel);

        logisticCourseModel.setCourse(courseModel);

        checkCourseActive(logisticCourseModel.getCourse());

        checkAgendaExists( logisticCourseModel.getCourse() );

        List<Date>agendaDays = logisticCourseModel.getCourse().getAgendaList().stream().map(
                agendaModel -> agendaModel.getCourseDate()
        ).collect(Collectors.toList());

        if ( logisticCourseService.existsLogisticCourse(logisticResponse.getId(), agendaDays) ) {
                throw new LogisticCourseAlreadyExistsException();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) auth.getAuthorities();
        Boolean tag = Boolean.FALSE;
        for(GrantedAuthority grantedAuthority: grantedAuthorityList){
            if(grantedAuthority.getAuthority().equalsIgnoreCase(RoleEnum.SUPER_ADMIN.getRole())){
                tag = Boolean.TRUE;
            }
        }
        if(tag){
            logisticCourseModel.setStatus(APPROVED);
        }else{
            logisticCourseModel.setStatus(StatusEnum.PENDING);
        }
        logisticCourseModel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return logisticCourseService.add(logisticCourseModel);
    }

    private void checkAgendaExists(CourseModel courseModel) {
        if ( courseModel.getAgendaList() == null || courseModel.getAgendaList().isEmpty() ) {
            throw new AgendaNotFoundException();
        }
    }

    private void checkCourseActive(CourseModel course) {
        if (  !course.getActive()  ) {
            throw new CourseBadStatusException();
        }
    }

}
