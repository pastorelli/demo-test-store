package es.innoit.zara.products.domain.model.valueobjects;

import es.innoit.zara.products.domain.exceptions.InvalidProductStockException;

public record ProductStock(int amount) {
    public ProductStock {
        if (amount < 0) {
            throw new InvalidProductStockException(
                    "El stock del producto no puede ser menor a cero: " + amount);
        }
    }
}
