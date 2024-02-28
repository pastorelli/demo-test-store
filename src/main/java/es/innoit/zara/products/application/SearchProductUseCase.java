package es.innoit.zara.products.application;

import java.util.List;
import java.util.Optional;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.ordercriteria.OrderCriteria;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;

public interface SearchProductUseCase {
    public List<Product> findAllProducts();

    public Optional<Product> findProductById(ProductId productId);

    public List<Product> findAllProducts(List<OrderCriteria> orderCriteria);
}
