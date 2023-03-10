package it.tcgroup.vilear.course;

import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtService;
import it.tcgroup.vilear.base.Payload.JWTServiceBase.JwtServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VilearCourseConfig {

    /**
     * Configurazione del Model Mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper;
    }

    @Bean
    public JwtService jwtService(){
        return new JwtServiceImpl();
    }
}
