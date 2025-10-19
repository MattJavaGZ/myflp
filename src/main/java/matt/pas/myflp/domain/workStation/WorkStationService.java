package matt.pas.myflp.domain.workStation;

import matt.pas.myflp.domain.workStation.dto.WorkStationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkStationService {

    private final WorkStationRepository workStationRepository;

    public WorkStationService(WorkStationRepository workStationRepository) {
        this.workStationRepository = workStationRepository;
    }

    public Optional<WorkStation> getWorkStationByName(String name) {
        return workStationRepository.findByName(name);
    }

    public List<WorkStationDto> getAllWorkStations() {
        return workStationRepository.findAll().stream()
                .map(WorkStationMapper::mapToWordStationDto)
                .toList();
    }
}
