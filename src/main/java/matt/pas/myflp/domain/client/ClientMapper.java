package matt.pas.myflp.domain.client;

import matt.pas.myflp.domain.client.dto.CilentToSaveDto;
import matt.pas.myflp.domain.client.dto.ClientDto;
import matt.pas.myflp.domain.clientGroup.ClientGroup;
import matt.pas.myflp.domain.clientGroup.ClientGroupService;
import matt.pas.myflp.domain.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ClientMapper {

    private ClientGroupService clientGroupService;

    public ClientMapper(ClientGroupService clientGroupService) {
        this.clientGroupService = clientGroupService;
    }

    public static ClientDto mapToClientDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAddress(),
                client.getEmail(),
                client.getPhone(),
                client.getFbLink(),
                client.getDateAdded(),
                client.getGroups().stream()
                        .map(ClientGroup::getName)
                        .toList()
        );
    }

    public static Client mapClientToSaveToClient(CilentToSaveDto cilentToSaveDto, User user, List<ClientGroup> groups) {
        final Client client = new Client();
        client.setFirstName(cilentToSaveDto.getFirstName());
        client.setLastName(cilentToSaveDto.getLastName());
        client.setEmail(cilentToSaveDto.getEmail());
        client.setPhone(cilentToSaveDto.getPhone());
        client.setFbLink(cilentToSaveDto.getFbLink());
        client.setAddress(cilentToSaveDto.getAddress());
        client.setUser(user);
        client.setDateAdded(LocalDateTime.now());
        client.setGroups(groups);
        return client;
    }

    public static CilentToSaveDto mapClientToClientToSave(Client client) {
        return new CilentToSaveDto(
                client.getFirstName(),
                client.getLastName(),
                client.getAddress(),
                client.getEmail(),
                client.getPhone(),
                client.getFbLink(),
                client.getGroups().stream()
                        .map(ClientGroup::getId)
                        .toList()
        );
    }
}
