package es.innoit.zara.products.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import es.innoit.zara.products.domain.exceptions.InvalidProductStockException;
import es.innoit.zara.products.domain.exceptions.ProductSizeVariantDoesNotExistException;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;

public class ProductStockTest {

    @Test
    public void incrementStockToProductVariant() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        assertEquals(new ProductStock(0), product.getStock(ProductSize.SMALL));
        ProductStock stock = new ProductStock(10);

        product.incrementVariantStock(ProductSize.SMALL, stock);

        assertEquals(stock, product.getStock(ProductSize.SMALL));
    }

    @Test
    public void shouldThrowExceptionIfStockIsLessThanZero() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));

        assertThrows(InvalidProductStockException.class,
                () -> product.incrementVariantStock(ProductSize.SMALL, new ProductStock(-1)),
                "El stock del producto no puede ser menor a cero: -1");
    }

    @Test
    public void decrementStockToProductVariant() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        product.incrementVariantStock(ProductSize.SMALL, new ProductStock(10));
        assertEquals(new ProductStock(10), product.getStock(ProductSize.SMALL));
        ProductStock stock = new ProductStock(5);

        product.decrementVariantStock(ProductSize.SMALL, stock);

        assertEquals(new ProductStock(5), product.getStock(ProductSize.SMALL));
    }

    @Test
    public void shouldThrowExceptionIfVariantDoesNotExists() {
        Product product =
                new Product(new ProductId(1), new ProductName("Nice Shirt"), ProductSize.SMALL);
        ProductStock stock = new ProductStock(5);

        assertThrows(ProductSizeVariantDoesNotExistException.class,
                () -> product.decrementVariantStock(ProductSize.MEDIUM, stock),
                "El producto no tiene la variante MEDIUM");

        assertThrows(ProductSizeVariantDoesNotExistException.class,
                () -> product.incrementVariantStock(ProductSize.MEDIUM, stock),
                "El producto no tiene la variante MEDIUM");
    }

    @Test
    public void shouldThrowExceptionIfStockIsNegative() {
        Product product =
                new Product(new ProductId(1), new ProductName("Nice Shirt"), ProductSize.SMALL);
        ProductStock stock = new ProductStock(1);

        assertThrows(InvalidProductStockException.class,
                () -> product.decrementVariantStock(ProductSize.SMALL, stock),
                "El stock del producto no puede ser menor a cero: -1");
    }

    @Test
    public void getAllProductVariantStockRemaining() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        product.incrementVariantStock(ProductSize.SMALL, new ProductStock(10));
        product.incrementVariantStock(ProductSize.MEDIUM, new ProductStock(10));
        product.incrementVariantStock(ProductSize.LARGE, new ProductStock(10));

        assertEquals(30, product.getTotalStockRemaining());
    }
}
