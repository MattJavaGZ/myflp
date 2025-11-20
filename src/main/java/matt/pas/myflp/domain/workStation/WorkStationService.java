package matt.pas.myflp.domain.workStation;

import matt.pas.myflp.domain.workStation.dto.WorkStationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationService {

    private final WorkStationRepository workStationRepository;

    public WorkStationService(WorkStationRepository workStationRepository) {
        this.workStationRepository = workStationRepository;
    }

    public List<WorkStationDto> getAllWorkStations() {
        return workStationRepository.findAll().stream()
                .map(WorkStationMapper::mapToWordStationDto)
                .toList();
    }
}
