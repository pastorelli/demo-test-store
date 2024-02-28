package es.innoit.zara.products.domain.exceptions;

public class ProductNameInvalidException extends IllegalArgumentException {
    public ProductNameInvalidException(String message) {
        super(message);
    }
}
