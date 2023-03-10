package it.tcgroup.vilear.registration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class VilearRegistrationConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper=new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper;
    }


    @Bean
    public Logger logger(){
        return Logger.getGlobal();
    }
}
