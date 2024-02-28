package es.innoit.zara.products.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.ordercriteria.OrderCriteria;
import es.innoit.zara.products.domain.model.ordercriteria.SalesOrderCriteria;
import es.innoit.zara.products.domain.model.ordercriteria.StockOrderCriteria;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;
import es.innoit.zara.products.domain.persistance.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class SearchProductServiceTest {

    private SearchProductUseCase searchProductUseCase;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        searchProductUseCase = new SearchProductService(productRepository);
    }

    @Test
    public void shouldReturnEmptyProductListIfNoProductFound() {

        when(productRepository.findAllProducts()).thenReturn(new ArrayList<>());

        List<Product> products = searchProductUseCase.findAllProducts();

        assertEquals(0, products.size());
    }

    @Test
    public void shouldReturnAllTheProducts() {
        List<Product> mockedProducts = mockProducts();
        when(productRepository.findAllProducts()).thenReturn(mockedProducts);

        List<Product> productsFound = searchProductUseCase.findAllProducts();

        assertEquals(6, productsFound.size());
        assertIterableEquals(mockedProducts, productsFound);
    }

    @Test
    public void shouldReturnTheProductGivenTheProductId() {
        when(productRepository.findProductById(any())).thenReturn(
                Optional.of(new Product(new ProductId(1), new ProductName("V-NECH BASIC SHIRT"))));

        Optional<Product> productFound = searchProductUseCase.findProductById(new ProductId(1));

        assertEquals(productFound.get().getId().id(), 1);
    }

    @Test
    public void givenStockOrderCriteriaShouldReturnTheProductsOrderByStockCriteria() {
        List<OrderCriteria> orderCriteria = new ArrayList<>();
        orderCriteria.add(new StockOrderCriteria(1));

        when(productRepository.findAllProducts()).thenReturn(mockProducts());

        List<Product> productsFound = searchProductUseCase.findAllProducts(orderCriteria);

        assertEquals(6, productsFound.size());
        assertEquals(4, productsFound.get(0).getId().id());
        assertEquals(2, productsFound.get(1).getId().id());
        assertEquals(3, productsFound.get(2).getId().id());
        assertEquals(6, productsFound.get(3).getId().id());
        assertEquals(1, productsFound.get(4).getId().id());
        assertEquals(5, productsFound.get(5).getId().id());
    }

    @Test
    public void givenSalesOrderCriteriaShouldReturnTheProductsOrderBySalesCriteria() {
        List<OrderCriteria> orderCriteria = new ArrayList<>();
        orderCriteria.add(new SalesOrderCriteria(1));

        when(productRepository.findAllProducts()).thenReturn(mockProducts());

        List<Product> productsFound = searchProductUseCase.findAllProducts(orderCriteria);

        assertEquals(6, productsFound.size());
        assertEquals(5, productsFound.get(0).getId().id());
        assertEquals(1, productsFound.get(1).getId().id());
        assertEquals(3, productsFound.get(2).getId().id());
        assertEquals(2, productsFound.get(3).getId().id());
        assertEquals(6, productsFound.get(4).getId().id());
        assertEquals(4, productsFound.get(5).getId().id());
    }

    private List<Product> mockProducts() {
        List<Product> products = new ArrayList<>();
        products.add(mockProduct(new ProductId(1), "V-NECH BASIC SHIRT", new int[] {4, 9, 0},
                new int[] {30, 30, 40}));
        products.add(mockProduct(new ProductId(2), "CONTRASTING FABRIC T-SHIRT",
                new int[] {35, 9, 9}, new int[] {10, 20, 20}));
        products.add(mockProduct(new ProductId(3), "RAISED PRINT T-SHIRT", new int[] {20, 2, 20},
                new int[] {30, 30, 20}));
        products.add(mockProduct(new ProductId(4), "PLEATED T-SHIRT", new int[] {25, 30, 10},
                new int[] {1, 1, 1}));
        products.add(mockProduct(new ProductId(5), "CONTRASTING LACE T-SHIRT", new int[] {0, 1, 0},
                new int[] {25, 25, 600}));
        products.add(mockProduct(new ProductId(6), "SLOGAN T-SHIRT", new int[] {9, 2, 5},
                new int[] {5, 5, 10}));
        return products;
    }

    private Product mockProduct(ProductId id, String name, int[] stock, int[] sales) {
        Product product = new Product(id, new ProductName(name), List.of());
        for (int i = 0; i < stock.length; i++) {
            product.addVariant(ProductSize.values()[i], new ProductStock(stock[i]),
                    new ProductStock(sales[i]));
        }
        return product;
    }
}
