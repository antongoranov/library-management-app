package bg.sfa_library_management.configuration;

import bg.sfa_library_management.dao.ClientDAO;
import bg.sfa_library_management.service.impl.ClientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic()
            .and()
                .authorizeRequests()
                .antMatchers("/","/login", "/register").anonymous()
                .antMatchers("/orders/create", "/authors", "/books").hasRole("USER")
                .antMatchers("/orders/**", "/authors/**", "/books/**", "/clients/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
                .logout(LogoutConfigurer::permitAll)
                .csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(ClientDAO clientDao) {
        return new ClientDetailsService(clientDao);
    }
}
