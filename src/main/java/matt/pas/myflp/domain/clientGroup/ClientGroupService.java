package matt.pas.myflp.domain.clientGroup;

import matt.pas.myflp.domain.clientGroup.dto.ClientGroupDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientGroupService {

    private ClientGroupRepository clientGroupRepository;


    public ClientGroupService(ClientGroupRepository clientGroupRepository) {
        this.clientGroupRepository = clientGroupRepository;
    }

    public List<ClientGroupDto> findAllClientGroups() {
        return clientGroupRepository.findAll().stream()
                .map(ClientGroupMapper::map)
                .toList();
    }

    public List<ClientGroup> findClientGroupsByGroupsId(List<Long> groupIds) {
        return clientGroupRepository.findAllById(groupIds);
    }

}
