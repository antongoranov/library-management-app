package bg.sfa_library_management.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryManagementConfig {

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }
}
