package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.user.User;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends ListCrudRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByUserAndClient_Id(User user, long id);

    List<Order> findAllByUserAndItems_Product_Id(User user, long id);

    List<Order> findAllByUserAndOrderDateBetween(User user, LocalDateTime start, LocalDateTime end);
}
