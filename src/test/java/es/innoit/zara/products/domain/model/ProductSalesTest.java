package es.innoit.zara.products.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import es.innoit.zara.products.domain.exceptions.InvalidProductStockException;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;

public class ProductSalesTest {

    @Test
    public void sellProduct() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        ProductStock initialStock = new ProductStock(10);
        product.incrementVariantStock(ProductSize.SMALL, initialStock);

        ProductStock productAmountSold = new ProductStock(5);
        product.sellProduct(ProductSize.SMALL, productAmountSold);

        assertEquals(initialStock.amount() - productAmountSold.amount(),
                product.getStock(ProductSize.SMALL).amount());
        assertEquals(productAmountSold.amount(), product.getUnitsSold(ProductSize.SMALL));
    }

    @Test
    public void shouldThrowExceptionWhenSellingMoreThanStock() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        product.incrementVariantStock(ProductSize.SMALL, new ProductStock(10));

        ProductStock productAmountSold = new ProductStock(11);

        assertThrows(InvalidProductStockException.class, () -> {
            product.sellProduct(ProductSize.SMALL, productAmountSold);
        }, "No hay suficiente stock para vender: " + productAmountSold.amount());
    }

    @Test
    public void getAllProductVariantsSold() {
        Product product = new Product(new ProductId(1), new ProductName("Nice Shirt"));
        product.incrementVariantStock(ProductSize.SMALL, new ProductStock(10));
        product.incrementVariantStock(ProductSize.MEDIUM, new ProductStock(10));
        product.incrementVariantStock(ProductSize.LARGE, new ProductStock(10));

        assertEquals(0, product.getTotalUnitsSold());

        ProductStock productAmountSold = new ProductStock(5);
        product.sellProduct(ProductSize.SMALL, productAmountSold);
        product.sellProduct(ProductSize.MEDIUM, productAmountSold);
        product.sellProduct(ProductSize.LARGE, productAmountSold);

        assertEquals(15, product.getTotalUnitsSold());
    }
}
