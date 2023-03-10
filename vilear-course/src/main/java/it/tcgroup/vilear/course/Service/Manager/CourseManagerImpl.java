package it.tcgroup.vilear.course.Service.Manager;

import it.tcgroup.vilear.base.Payload.Exception.CourseNotFoundException;
import it.tcgroup.vilear.course.Entity.CourseEntity;
import it.tcgroup.vilear.course.Model.CourseModel;
import it.tcgroup.vilear.course.Repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseManagerImpl implements CourseManager{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseEntity save(CourseModel courseModel) {
        CourseEntity courseEntity = mapper.map(courseModel, CourseEntity.class);
        // Salva i dati del Corso
        return courseRepository.save(courseEntity);
    }

    @Override
    public CourseEntity get(Integer courseId) {
       return courseRepository
               .findById(courseId)
               .orElseThrow(()->new CourseNotFoundException());
    }

    // Soft Delete
   @Override
    public void delete(Integer courseId) {
        if(courseRepository.existsById(courseId)){
            CourseEntity courseEntity=courseRepository.findById(courseId).get();
            courseEntity.setActive(false);
            courseRepository.save(courseEntity);
        }else{
            throw new CourseNotFoundException();
        }
    }

}
