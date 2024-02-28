package es.innoit.zara.products.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import es.innoit.zara.products.domain.exceptions.ProductNameInvalidException;
import es.innoit.zara.products.domain.exceptions.ProductSizeVariantAlreadyExistsException;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;

public class ProductTest {

    @Test
    public void createProductGivenANameAndId() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name);

        assertEquals("Nice Shirt", product.getName());
    }

    @Test
    public void failsIfProductIdIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new ProductId(-1),
                "El id del producto no puede ser menor o igual a cero: -1");
    }

    @ParameterizedTest(name = "fails if product name is empty or null")
    @NullAndEmptySource
    public void failsIfProductNameIsEmptyOrNull(String name) {
        assertThrows(ProductNameInvalidException.class, () -> new ProductName(name),
                "El nombre del producto no puede estar vacío");
    }

    @Test
    public void failsIfProductNameIsLongerThan255() {
        String name = "P".repeat(256);
        assertThrows(ProductNameInvalidException.class, () -> new ProductName(name),
                "El nombre del producto no puede tener más de 255 caracteres");
    }

    @Test
    public void createProductWithADefaultVariantsIfNoneGiven() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name);

        assertEquals(3, product.getVariantsSize());
        assertIterableEquals(List.of(ProductSize.values()), product.getVariantsNames());
    }

    @Test
    public void createProductGivenAVariant() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name, ProductSize.SMALL);

        assertEquals(1, product.getVariantsSize());
        assertIterableEquals(List.of(ProductSize.SMALL), product.getVariantsNames());
    }

    @Test
    public void failsIfProductVariantAlreadyExists() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name, ProductSize.SMALL);

        assertThrows(ProductSizeVariantAlreadyExistsException.class,
                () -> product.addVariant(ProductSize.SMALL), "El producto " + name.Name()
                        + " ya tiene una variante con el nombre " + ProductSize.SMALL);
    }

    @Test
    public void addVariantProduct() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name, ProductSize.SMALL);

        product.addVariant(ProductSize.MEDIUM);

        assertEquals(2, product.getVariantsSize());
    }

    @Test
    public void removeVariantProduct() {
        ProductName name = new ProductName("Nice Shirt");
        ProductId id = new ProductId(1);
        Product product = new Product(id, name);

        product.removeVariant(ProductSize.MEDIUM);

        assertEquals(2, product.getVariantsSize());
        assertEquals(false, product.variantExists(ProductSize.MEDIUM));
    }
}
