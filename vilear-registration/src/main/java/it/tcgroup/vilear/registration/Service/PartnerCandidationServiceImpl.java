package it.tcgroup.vilear.registration.Service;

import it.tcgroup.vilear.base.Payload.Enum.DecisionEnum;
import it.tcgroup.vilear.base.Payload.Enum.StatusEnum;
import it.tcgroup.vilear.base.Payload.Exception.PartnerCandidationAlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Exception.UserAlreadyExistsException;
import it.tcgroup.vilear.base.Payload.Response.PaginatedResponse;
import it.tcgroup.vilear.base.Payload.Response.PartnerResponse;
import it.tcgroup.vilear.base.Payload.Service.PaginationUtils;
import it.tcgroup.vilear.registration.Entity.PartnerCandidationEntity;
import it.tcgroup.vilear.registration.Model.PartnerCandidationModel;
import it.tcgroup.vilear.registration.Model.UserModel;
import it.tcgroup.vilear.registration.Service.Manager.PartnerCandidationManager;
import it.tcgroup.vilear.registration.Client.ServiceClient.VilearPartnerServiceClient;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;


@Service
public class PartnerCandidationServiceImpl implements PartnerCandidationService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PartnerCandidationManager partnerCandidationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private VilearPartnerServiceClient vilearPartnerServiceClient;


    /**
     * Verifica se lo stato della candidatura del Partner sia già Approvato o Rifiutato
     * @param partnerCandidationModel
     * @throws PartnerCandidationAlreadyExistsException
     * */
    private void checkPartnerCandidationStatus(PartnerCandidationModel partnerCandidationModel) {
        switch(partnerCandidationModel.getStatus()){
            case APPROVED -> throw new PartnerCandidationAlreadyExistsException();
            case REJECTED -> throw new PartnerCandidationAlreadyExistsException();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PartnerCandidationModel candidate(PartnerCandidationModel partnerCandidationModel) {
        /*
         * Esegue i seguenti controlli:
         *      - esistenza del partner
         *      - esistenza dell'utenza
         *      - esistenza dell'email associata all'utenza
         */
        // Controllo se questo utente non esista già come persona Normale o Partner
        UserModel userModel = userService.getByEmail(partnerCandidationModel.getUser().getEmail());
        if (userModel != null) {
            throw new UserAlreadyExistsException();
        }
        // Devo salvare l'utente
        userModel = partnerCandidationModel.getUser();
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setActive(false);

        //Salvo i dati dello User
        UserModel newUser = userService.add(userModel);
        // Imposto l'utente nella Partecipazione
        partnerCandidationModel.setUser(newUser);

        // Recupero i dati raccolti del Partner e salvo sul Client del Partner
        PartnerResponse partnerResponse = vilearPartnerServiceClient.addPartner(partnerCandidationModel);

        // Imposto lo Status del Partner
        partnerCandidationModel.setStatus(StatusEnum.PENDING);
        //Aggiungo la candidatura
        return add(partnerCandidationModel);
    }



    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public PartnerCandidationModel choose(Integer userId, DecisionEnum decision) {
        // Verifica l'esistenza della candidatura
        PartnerCandidationModel partnerCandidationModel = getByUser(userId);

        // Verifica che la candidatura non sia già stata valutata
        checkPartnerCandidationStatus(partnerCandidationModel);
        switch (decision) {
            case ACCEPT: {
                partnerCandidationModel.setStatus(StatusEnum.APPROVED);
                partnerCandidationModel.getUser().setActive(true);
                break;
            }
            case DENY: {
                partnerCandidationModel.setStatus(StatusEnum.REJECTED);
            }
        }
        // todo invia una mail all'utente dicendo che la sua candidatura è stata Accettata

        // Imposta la data della decisione presa
        partnerCandidationModel.setDecisionDate( Timestamp.from(Instant.now()) );
        // Aggiorna Il Partner
        PartnerCandidationEntity partnerCandidationEntity = partnerCandidationManager.save(partnerCandidationModel);

        return mapper.map(partnerCandidationEntity, PartnerCandidationModel.class);
    }

    private PartnerCandidationModel add(PartnerCandidationModel partnerCandidationModel) {
        // Salva la Candidatura del Partner
        PartnerCandidationEntity partnerCandidationEntity = partnerCandidationManager.save(partnerCandidationModel);

        return mapper.map(partnerCandidationEntity, PartnerCandidationModel.class);
    }

    @Override
    @Transactional
    public PartnerCandidationModel get(Integer partnerCandidationId){
        PartnerCandidationEntity partnerCandidationEntity = partnerCandidationManager.get(partnerCandidationId);

        return mapper.map(partnerCandidationEntity, PartnerCandidationModel.class);
    }

    @Override
    @Transactional
    public PartnerCandidationModel getByUser(Integer userId){
        PartnerCandidationEntity partnerCandidationEntity = partnerCandidationManager.getByUser(userId);

        return mapper.map(partnerCandidationEntity, PartnerCandidationModel.class);
    }

    @Override
    @Transactional
    public PaginatedResponse<PartnerCandidationModel> getPartnerCandidations(Integer page, Integer pageSize) {
        PageRequest pageRequest = PaginationUtils.buildPageRequest(page, pageSize);
        Page<PartnerCandidationEntity> partnerCandidationEntityPage = partnerCandidationManager.getAll(pageRequest);
        List<PartnerCandidationModel> partnerCandidationModelList = mapper.map(
                partnerCandidationEntityPage.getContent(),
                new TypeToken<List<PartnerCandidationModel>>(){}.getType());
        PaginatedResponse.PageData pageData = new PaginatedResponse.PageData();
        pageData.setTotalPages(partnerCandidationEntityPage.getTotalPages());
        pageData.setTotalElements(partnerCandidationEntityPage.getTotalElements());
        return new PaginatedResponse<PartnerCandidationModel>(
            partnerCandidationModelList,
                pageData
        );
    }


}
