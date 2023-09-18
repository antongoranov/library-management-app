package bg.sfa_library_management.web;

import bg.sfa_library_management.model.dtos.view.AuthorViewDTO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;
import bg.sfa_library_management.service.ClientService;
import bg.sfa_library_management.service.impl.AuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientViewDTO>> allClients() throws SQLException {
        List<ClientViewDTO> fetchedClients = this.clientService.getAllClients();

        return ResponseEntity.ok(fetchedClients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientViewDTO> getAuthorById(@PathVariable long id) throws SQLException {

        Optional<ClientViewDTO> optAuthorDto = this.clientService.findById(id);

        if (optAuthorDto.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(optAuthorDto.get());
    }
}
