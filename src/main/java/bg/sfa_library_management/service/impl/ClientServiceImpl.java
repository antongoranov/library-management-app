package bg.sfa_library_management.service.impl;

import bg.sfa_library_management.dao.ClientDAO;
import bg.sfa_library_management.model.dtos.view.ClientViewDTO;
import bg.sfa_library_management.model.entity.Client;
import bg.sfa_library_management.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO,
                             ModelMapper modelMapper) {
        this.clientDAO = clientDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<ClientViewDTO> findById(long id) throws SQLException {
        return this.clientDAO.findClientById(id)
                .map(this::clientToDTOMapper);
    }

    @Override
    public List<ClientViewDTO> getAllClients() throws SQLException {
        List<Client> fetchedClients = this.clientDAO.findAllClients();

        return fetchedClients
                .stream()
                .map(this::clientToDTOMapper)
                .collect(Collectors.toList());
    }

    private ClientViewDTO clientToDTOMapper(Client client) {
        return this.modelMapper.map(client, ClientViewDTO.class);
    }
}
