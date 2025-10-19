package matt.pas.myflp.domain.client;

import matt.pas.myflp.domain.user.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

     List<Client> findAllByUser(User user);
}
