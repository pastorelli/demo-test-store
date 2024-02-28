package es.innoit.zara.products.domain.exceptions;

public class ProductSizeVariantAlreadyExistsException extends IllegalArgumentException {
    public ProductSizeVariantAlreadyExistsException(String message) {
        super(message);
    }
}
