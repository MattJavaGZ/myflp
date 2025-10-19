package matt.pas.myflp.domain.clientGroup;

import matt.pas.myflp.domain.clientGroup.dto.ClientGroupDto;

public class ClientGroupMapper {

    public static ClientGroupDto map(ClientGroup clientGroup) {
        return new ClientGroupDto(
                clientGroup.getId(),
                clientGroup.getName()
        );
    }
}
