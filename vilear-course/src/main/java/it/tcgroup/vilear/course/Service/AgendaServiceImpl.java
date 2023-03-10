package it.tcgroup.vilear.course.Service;

import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.AgendaBadCalculateException;
import it.tcgroup.vilear.base.Payload.Exception.CourseBadStatusException;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.course.Entity.AgendaEntity;
import it.tcgroup.vilear.course.Model.AgendaModel;
import it.tcgroup.vilear.course.Model.CourseModel;
import it.tcgroup.vilear.course.Model.HolidayDate;
import it.tcgroup.vilear.course.Service.Manager.AgendaManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DateService dateService;

    @Autowired
    private AgendaManager agendaManager;

    @Override
    @Transactional
    public AgendaModel add(AgendaModel agendaModel) {
        // Salvo i Dati dell'agenda
        AgendaEntity agendaEntity = agendaManager.save(agendaModel);

        return mapper.map(agendaEntity,AgendaModel.class);
    }

    @Override
    @Transactional
    public AgendaModel get(Integer agendaId, Integer courseId) {
        AgendaEntity agendaEntity = agendaManager.get(agendaId,courseId);

        return mapper.map(agendaEntity,AgendaModel.class);
    }


    @Override
    @Transactional
    public PaginatedResponse<AgendaModel>getByCourse(Integer courseId, PageRequest pageRequest){
        Page<AgendaEntity> agendaEntityPage = agendaManager.getByCourse(courseId,pageRequest);
        List<AgendaModel>agendaModelList = mapper.map(agendaEntityPage.getContent(),new TypeToken<List<AgendaModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalElements(agendaEntityPage.getTotalElements());
        pageData.setTotalPages(agendaEntityPage.getTotalPages());

        PaginatedResponse<AgendaModel>agendaModelPaginatedResponse = new PaginatedResponse<>();
        agendaModelPaginatedResponse.setPageData(pageData);
        agendaModelPaginatedResponse.setData(agendaModelList);

        return agendaModelPaginatedResponse;
    }

    @Override
    @Transactional
    public AgendaModel update(AgendaModel agendaModifyModel, Integer agendaId, Integer courseId) {
            // Recupero i dati dell'Agenda
            AgendaModel agendaModel= get(agendaId,courseId);
            // Imposto i dati
            agendaModel.setCourseDate(agendaModifyModel.getCourseDate());
            agendaModel.setTimeBegin(agendaModifyModel.getTimeBegin());
            agendaModel.setTimeEnd(agendaModifyModel.getTimeEnd());
            // Salva i dati
            AgendaEntity agendaEntity = agendaManager.save(agendaModifyModel);
            return agendaModifyModel;
    }

    @Override
    @Transactional
    public void delete(Integer agendaId, Integer courseId) {
       agendaManager.delete(agendaId,courseId);
    }

    /**
     * Questo metodo prende in input due dati :
     * I dati del corso
     * I dati dell'agenda
     * - Inizialmente recupero la data di Inizio corso
     * - Recupero la data di Fine Corso
     *
     * 1) Recupero l'intervallo di giorni che intercorre tra inizio e fine corso
     * 1.1) Recupero tutti i giorni festivi che ci sono in un anno
     *
     * 2) Ciclo per il valore dell'intervallo dei giorni :
     * 3) Verifico che ogni giorno sia un giorno di Festa o di lavoro
     * 4) getDayOfWeek mi dice se quel giorno è sabato o domenica :
     *    => Aggiunge i giorni all'insieme dei giorni Festivi
     *    Altrimenti lo aggiunge alla lista dei giorni lavorativi
     * @param courseModel dati del corso
     * @param agendaModel dati dell'agenda
     * @return una lista dei giorni lavorativi
     */

    @Override
    @Transactional
    public List<AgendaModel> calculate(CourseModel courseModel, AgendaModel agendaModel) {

        if(     !courseModel.getActive() ||
                courseModel.getStatus().equals(StatusEnum.PENDING) ||
                courseModel.getStatus().equals(StatusEnum.REJECTED)
        ){
            throw new CourseBadStatusException();
        }

        // Mi serve per recuperare tutti i corsi che si tengono in quei giorni
        List<AgendaModel>agendaModelList = courseModel.getAgendaList();
        if(agendaModelList.size()>1){
            throw new AgendaBadCalculateException();
        }

        // Recupero la data iniziale del Corso
        Date dateStart = courseModel.getStartDate();
        Date dateEnd = courseModel.getEndDate();

        // Devo calcolare il numero di giorni che intercorrono tra una data e l'altra
        // Imposto tutti i dati relativi alla Dat
        HolidayDate startHolidayDate=dateService.fromDateToHolidayDate(dateStart);
        HolidayDate endHolidayDate=dateService.fromDateToHolidayDate(dateEnd);

        // Recupera tutti i giorni dell'anno che sono di festa
        LinkedList<HolidayDate> holidayDateSet = dateService.createHolidayDate(startHolidayDate.getYear());
        // Giorni sul quale ciclare e rappresenta il numero di giorni che intercorrono tra due date
        Long numberOfDayToFinish = dateService.getDayToEnd(startHolidayDate,endHolidayDate);

        // Imposto i giorni Festivi
        // Mi calcola in automatico anche la pasqua e pasquetta

        // Adesso devo verificare solo che è sabato o domenica
        // Se queso giorno è uno di quelli li aggiunge all'insieme di Holiday
        HolidayDate initHolidayDate = startHolidayDate;
        Time timeBegin = agendaModel.getTimeBegin();// Orario di Inizio
        Time timeEnd = agendaModel.getTimeEnd();// Orario di fine Corso
        // Ciclo fino a quando i giorni lavorativi non sono ridotti a zero
        while(numberOfDayToFinish>0) {

            if(!dateService.isHoliday(initHolidayDate)) {
                switch (dateService.getDayOfWeek(initHolidayDate)) {
                    case SABATO: {
                        dateService.addHolidayDate(initHolidayDate);
                        break;
                    }
                    case DOMENICA: {
                        dateService.addHolidayDate(initHolidayDate);
                        break;
                    }
                    case LUNEDI: {
                        AgendaModel newAgendaModel = new AgendaModel();
                        // Imposto i dati dell'agenda
                        newAgendaModel.setCourse(courseModel);
                        newAgendaModel.setTimeEnd(timeEnd);
                        newAgendaModel.setTimeBegin(timeBegin);
                        newAgendaModel.setCourseDate(dateService.fromHolidayDateToDate(initHolidayDate));
                        newAgendaModel = add(newAgendaModel);
                        agendaModelList.add(newAgendaModel);
                        break;
                    }
                    case MARTEDI: {
                        AgendaModel newAgendaModel = new AgendaModel();
                        // Imposto i dati dell'agenda
                        newAgendaModel.setCourse(courseModel);
                        newAgendaModel.setTimeEnd(timeEnd);
                        newAgendaModel.setTimeBegin(timeBegin);
                        newAgendaModel.setCourseDate(dateService.fromHolidayDateToDate(initHolidayDate));
                        newAgendaModel = add(newAgendaModel);
                        agendaModelList.add(newAgendaModel);
                        break;
                    }
                    case MERCOLEDI: {
                        AgendaModel newAgendaModel = new AgendaModel();
                        // Imposto i dati dell'agenda
                        newAgendaModel.setCourse(courseModel);
                        newAgendaModel.setTimeEnd(timeEnd);
                        newAgendaModel.setTimeBegin(timeBegin);
                        newAgendaModel.setCourseDate(dateService.fromHolidayDateToDate(initHolidayDate));
                        newAgendaModel = add(newAgendaModel);
                        agendaModelList.add(newAgendaModel);
                        break;
                    }
                    case GIOVEDI: {
                        AgendaModel newAgendaModel = new AgendaModel();
                        // Imposto i dati dell'agenda
                        newAgendaModel.setCourse(courseModel);
                        newAgendaModel.setTimeEnd(timeEnd);
                        newAgendaModel.setTimeBegin(timeBegin);
                        newAgendaModel.setCourseDate(dateService.fromHolidayDateToDate(initHolidayDate));
                        newAgendaModel = add(newAgendaModel);
                        agendaModelList.add(newAgendaModel);
                        break;
                    }
                    case VENERDI: {
                        AgendaModel newAgendaModel = new AgendaModel();
                        // Imposto i dati dell'agenda
                        newAgendaModel.setCourse(courseModel);
                        newAgendaModel.setTimeEnd(timeEnd);
                        newAgendaModel.setTimeBegin(timeBegin);
                        newAgendaModel.setCourseDate(dateService.fromHolidayDateToDate(initHolidayDate));
                        newAgendaModel = add(newAgendaModel);
                        agendaModelList.add(newAgendaModel);
                        break;
                    }
                }
            }
            // Aggiungo un giorno fino a quando non me ne restano più
            initHolidayDate=dateService.plusDayToDate(initHolidayDate);
            numberOfDayToFinish--;
        }
        // Ho tutti i giorni in quel determinato frangente che sono Holiday
        // ho inserito tutti i dati di un' agenda per un determinato corso
        return agendaModelList;
    }

    @Override
    @Transactional
    public PaginatedResponse<AgendaModel> getAll( PageRequest pageRequest ) {
        Page<AgendaEntity>agendaEntityPage = agendaManager.findAll(pageRequest);

        List<AgendaModel>agendaModelList = mapper.map(agendaEntityPage.getContent(),new TypeToken<List<AgendaModel>>(){}.getType());

        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(agendaEntityPage.getTotalPages());
        pageData.setTotalElements(agendaEntityPage.getTotalElements());

        PaginatedResponse<AgendaModel>paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setData(agendaModelList);
        paginatedResponse.setPageData(pageData);
        return paginatedResponse;
    }

}
