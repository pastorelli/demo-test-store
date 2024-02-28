package es.innoit.zara.products.domain.exceptions;

public class ProductSizeVariantDoesNotExistException extends IllegalArgumentException {
    public ProductSizeVariantDoesNotExistException(String message) {
        super(message);
    }
}
