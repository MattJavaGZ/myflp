package matt.pas.myflp.domain.workStation;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface WorkStationRepository extends ListCrudRepository<WorkStation, Long> {
    Optional<WorkStation> findByName(String name);
}
