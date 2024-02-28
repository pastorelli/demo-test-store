package es.innoit.zara.products.infrastructure.persistance.h2implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;

@DataJpaTest
@Import(H2ProductRepository.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class h2ProductRepositoryTest {

    @Autowired
    private H2ProductRepository productRepository;

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = productRepository.findAllProducts();

        assertEquals(6, products.size());
    }

    @Test
    public void shouldReturnOptionalEmptyIfProductDoesNotExist() {
        Optional<Product> product = productRepository.findProductById(new ProductId(10));
        assertEquals(Optional.empty(), product);
    }

    @Test
    public void shouldReturnTheProductGivenTheProductId() {
        Optional<Product> product = productRepository.findProductById(new ProductId(1));
        assertEquals(1, product.get().getId().id());
    }
}
