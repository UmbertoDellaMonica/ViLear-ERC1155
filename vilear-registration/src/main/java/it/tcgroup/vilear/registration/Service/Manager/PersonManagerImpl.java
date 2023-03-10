package it.tcgroup.vilear.registration.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.PersonNotFoundException;
import it.tcgroup.vilear.registration.Entity.PersonEntity;
import it.tcgroup.vilear.registration.Model.PersonModel;
import it.tcgroup.vilear.registration.Repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonManagerImpl implements PersonManager{
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public PersonEntity save(PersonModel personModel){
       PersonEntity personEntity =  mapper.map(personModel, PersonEntity.class);

       return personRepository.save(personEntity);
    }

    @Override
    public PersonEntity get(Integer personId){
       return personRepository
               .findById(personId).orElseThrow(()->new PersonNotFoundException());
    }

    @Override
    public void delete(Integer personId){
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
        } else {
            throw new PersonNotFoundException();
        }
    }

}
