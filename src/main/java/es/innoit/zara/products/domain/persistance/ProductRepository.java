package es.innoit.zara.products.domain.persistance;

import java.util.List;
import java.util.Optional;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;

public interface ProductRepository {
    List<Product> findAllProducts();

    Optional<Product> findProductById(ProductId id);
}
