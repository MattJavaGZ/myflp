package matt.pas.myflp.domain.clientGroup;

import matt.pas.myflp.domain.clientGroup.dto.ClientGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientGroupService {

    private final ClientGroupRepository clientGroupRepository;


    public ClientGroupService(ClientGroupRepository clientGroupRepository) {
        this.clientGroupRepository = clientGroupRepository;
    }

    public List<ClientGroupDto> findAllClientGroups() {
        return clientGroupRepository.findAll().stream()
                .map(ClientGroupMapper::map)
                .toList();
    }


}
