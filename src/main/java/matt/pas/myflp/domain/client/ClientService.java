package matt.pas.myflp.domain.client;

import jakarta.transaction.Transactional;
import matt.pas.myflp.domain.client.dto.CilentToSaveDto;
import matt.pas.myflp.domain.client.dto.ClientDto;
import matt.pas.myflp.domain.clientGroup.ClientGroup;
import matt.pas.myflp.domain.clientGroup.ClientGroupService;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.domain.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service()
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientGroupService clientGroupService;
    private final UserService userService;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientGroupService clientGroupService, UserService userService,
                         ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientGroupService = clientGroupService;
        this.userService = userService;
        this.clientMapper = clientMapper;
    }

    public List<ClientDto> findAllCientsByUser() {
        final User user = userService.getCurrentUser();
        return clientRepository.findAllByUser(user).stream()
                .map(ClientMapper::mapToClientDto)
                .sorted()
                .toList();
    }

    public List<ClientDto> findClientsByUserWord(String word) {
        final User user = userService.getCurrentUser();
        return clientRepository.findAllByUser(user).stream()
                .filter(client -> searchInClient(client, word))
                .map(ClientMapper::mapToClientDto)
                .toList();
    }

    private boolean searchInClient(Client client, String userWord) {
        final String[] split = userWord.toLowerCase().split(" ");

        for (String word : split) {
            if (client.getFirstName().toLowerCase().contains(word) ||
            client.getLastName().toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    public void addNewClient(CilentToSaveDto client) {
        final User user = userService.getCurrentUser();
        final List<ClientGroup> clientGroups = clientGroupService.findClientGroupsByGroupsId(client.getGroupIds());
        final Client clientToSave = ClientMapper.mapClientToSaveToClient(client, user, clientGroups);
        clientRepository.save(clientToSave);
    }

    public Optional<ClientDto> findClientDtoById(Long id) {
        return clientRepository.findById(id)
                .map(ClientMapper::mapToClientDto);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }
    public Optional<CilentToSaveDto> findClientToSaveById(Long id){
        return clientRepository.findById(id)
                .map(ClientMapper::mapClientToClientToSave);
    }

    public String getFirstAndLastNameClient(long id) {
        final Client client = clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return client.getFirstName() + " " + client.getLastName();
    }

    public boolean verifiClient(long clientId){
        final User user = userService.getCurrentUser();
        final Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return client.getUser().equals(user);
    }
    @Transactional
    public void editClient(long clientId, CilentToSaveDto client) {
        final Client clientToEdit = clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        clientToEdit.setFirstName(client.getFirstName());
        clientToEdit.setLastName(client.getLastName());
        clientToEdit.setAddress(client.getAddress());
        clientToEdit.setEmail(client.getEmail());
        clientToEdit.setPhone(client.getPhone());
        clientToEdit.setFbLink(client.getFbLink());
        clientToEdit.setGroups(clientGroupService.findClientGroupsByGroupsId(client.getGroupIds()));
    }
}
