package es.innoit.zara.products.application;

import java.util.List;
import java.util.Optional;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.ordercriteria.OrderCriteria;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.persistance.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchProductService implements SearchProductUseCase {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    public Optional<Product> findProductById(ProductId productId) {
        return productRepository.findProductById(productId);
    }

    public List<Product> findAllProducts(List<OrderCriteria> orderCriteria) {
        return productRepository.findAllProducts()
                .stream()
                .sorted((p1, p2) -> compareCriteria(p1, p2, orderCriteria))
                .toList();
    }

    private int compareCriteria(Product p1, Product p2,
            List<OrderCriteria> orderCriteria) {
        int sumCriteriaP1 = orderCriteria.stream()
                .mapToInt(criteria -> criteria.getOrderWeight(p2)).sum();
        int sumCriteriaP2 = orderCriteria.stream()
                .mapToInt(criteria -> criteria.getOrderWeight(p1)).sum();
        return sumCriteriaP1 - sumCriteriaP2;
    }
}
