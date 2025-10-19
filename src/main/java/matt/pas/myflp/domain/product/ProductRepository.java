package matt.pas.myflp.domain.product;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductRepository extends ListCrudRepository<Product, Long> {

    List<Product> findAllByNameContainingIgnoreCaseOrPartNumberContainingIgnoreCase(String word1, String word2);
}
