package bg.sfa_library_management.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBManagerConfiguration {

    @Value("${spring.datasource.driver-class-name}") String jdbcDriver;
    @Value("${spring.datasource.url}") String jdbcUrl;
    @Value("${spring.datasource.username}") String jdbcUser;
    @Value("${spring.datasource.password}") String jdbcPassword;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url(jdbcUrl)
                .password(jdbcPassword)
                .username(jdbcUser)
                .driverClassName(jdbcDriver)
                .build();
    }
}
