package matt.pas.myflp.domain.workStation;

import matt.pas.myflp.domain.workStation.dto.WorkStationDto;

public class WorkStationMapper {

    public static WorkStationDto mapToWordStationDto (WorkStation workStation) {
        return new WorkStationDto(
                workStation.getName()
        );
    }
}
