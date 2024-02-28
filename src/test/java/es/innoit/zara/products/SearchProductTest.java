package es.innoit.zara.products;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import es.innoit.zara.products.infrastructure.web.dto.ProductDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SearchProductTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnAllTheProductsSortedByStockCriteria() {
        ParameterizedTypeReference<List<ProductDTO>> responseType =
                new ParameterizedTypeReference<List<ProductDTO>>() {};
        ResponseEntity<List<ProductDTO>> responseEntity =
                restTemplate.exchange("/api/products?StockOrderCriteria=1&SalesOrderCriteria=0",
                        HttpMethod.GET, null, responseType);

        List<ProductDTO> products = responseEntity.getBody();

        assertNotNull(products);
        assertEquals(6, products.size());
        assertEquals(4, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());
        assertEquals(6, products.get(3).getId());
        assertEquals(1, products.get(4).getId());
        assertEquals(5, products.get(5).getId());
    }

    @Test
    public void shouldReturnAllTheProductsSortedBySalesCriteria() {
        ParameterizedTypeReference<List<ProductDTO>> responseType =
                new ParameterizedTypeReference<List<ProductDTO>>() {};
        ResponseEntity<List<ProductDTO>> responseEntity =
                restTemplate.exchange("/api/products?StockOrderCriteria=0&SalesOrderCriteria=1",
                        HttpMethod.GET, null, responseType);

        List<ProductDTO> products = responseEntity.getBody();

        assertNotNull(products);
        assertEquals(6, products.size());
        assertEquals(5, products.get(0).getId());
        assertEquals(1, products.get(1).getId());
        assertEquals(3, products.get(2).getId());
        assertEquals(2, products.get(3).getId());
        assertEquals(6, products.get(4).getId());
        assertEquals(4, products.get(5).getId());
    }

    @Test
    public void shouldReturnAllTheProductsSortedById() {
        ParameterizedTypeReference<List<ProductDTO>> responseType =
                new ParameterizedTypeReference<List<ProductDTO>>() {};
        ResponseEntity<List<ProductDTO>> responseEntity =
                restTemplate.exchange("/api/products?StockOrderCriteria=0&SalesOrderCriteria=0",
                        HttpMethod.GET, null, responseType);

        List<ProductDTO> products = responseEntity.getBody();

        assertNotNull(products);
        assertEquals(6, products.size());
        assertEquals(1, products.get(0).getId());
        assertEquals(2, products.get(1).getId());
        assertEquals(3, products.get(2).getId());
        assertEquals(4, products.get(3).getId());
        assertEquals(5, products.get(4).getId());
        assertEquals(6, products.get(5).getId());
    }
}
