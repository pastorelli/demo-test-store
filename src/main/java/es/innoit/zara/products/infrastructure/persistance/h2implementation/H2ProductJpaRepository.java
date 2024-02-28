package es.innoit.zara.products.infrastructure.persistance.h2implementation;

import org.springframework.data.jpa.repository.JpaRepository;
import es.innoit.zara.products.infrastructure.persistance.h2implementation.entity.ProductJpaEntity;

public interface H2ProductJpaRepository extends JpaRepository<ProductJpaEntity, Integer> {
}
