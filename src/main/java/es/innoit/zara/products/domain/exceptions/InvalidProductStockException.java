package es.innoit.zara.products.domain.exceptions;

public class InvalidProductStockException extends IllegalArgumentException {
    public InvalidProductStockException(String message) {
        super(message);
    }
}
