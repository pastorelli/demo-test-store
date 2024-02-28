package es.innoit.zara.products.infrastructure.persistance.h2implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.persistance.ProductRepository;
import es.innoit.zara.products.infrastructure.persistance.h2implementation.entity.ProductJpaEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class H2ProductRepository implements ProductRepository {

    private final H2ProductJpaRepository h2ProductJpaRepository;

    @Override
    public List<Product> findAllProducts() {
        return h2ProductJpaRepository.findAll().stream().map(ProductJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findProductById(ProductId id) {
        return h2ProductJpaRepository.findById(Integer.valueOf(id.id()))
                .map(ProductJpaEntity::toDomain);
    }
}
