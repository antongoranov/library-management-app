package bg.sfa_library_management.web;

import bg.sfa_library_management.model.dtos.binding.ClientRegistrationDTO;
import bg.sfa_library_management.model.dtos.binding.ClientLoginDTO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;
import bg.sfa_library_management.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService loginService) {
        this.authService = loginService;
    }


    //LOGIN
    //if login is unsuccessful throws an exception
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ClientLoginDTO clientLoginDTO) {

        this.authService.authenticateUser(clientLoginDTO.getUsername(), clientLoginDTO.getPassword());

        return ResponseEntity.ok("User login is successful!");
    }

    //REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody ClientRegistrationDTO clientRegistrationDTO,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws SQLException {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .collect(Collectors.joining("\n")));
        }

        ClientViewDTO registeredClientDTO = this.authService.register(clientRegistrationDTO);

        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .path("/clients/{id}")
                                .build(registeredClientDTO.getId()))
                .body("Registration successful!");
    }
}

