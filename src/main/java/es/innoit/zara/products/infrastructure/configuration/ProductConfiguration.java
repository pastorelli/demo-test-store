package es.innoit.zara.products.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import es.innoit.zara.products.application.SearchProductService;
import es.innoit.zara.products.application.SearchProductUseCase;
import es.innoit.zara.products.domain.persistance.ProductRepository;
import es.innoit.zara.products.infrastructure.persistance.h2implementation.H2ProductJpaRepository;
import es.innoit.zara.products.infrastructure.persistance.h2implementation.H2ProductRepository;

@Configuration
public class ProductConfiguration {

    @Bean
    public SearchProductUseCase searchProductService(ProductRepository productRepository) {
        return new SearchProductService(productRepository);
    }

    @Bean
    public ProductRepository productRepository(H2ProductJpaRepository h2ProductJpaRepository) {
        return new H2ProductRepository(h2ProductJpaRepository);
    }
}
