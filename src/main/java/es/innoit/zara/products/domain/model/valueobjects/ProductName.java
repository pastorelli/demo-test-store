package es.innoit.zara.products.domain.model.valueobjects;

import es.innoit.zara.products.domain.exceptions.ProductNameInvalidException;

public record ProductName(String Name) {
    public static int MAX_LENGTH = 255;

    public ProductName {
        if (Name == null || Name.isBlank()) {
            throw new ProductNameInvalidException("El nombre del producto no puede estar vacío");
        }
        if (Name.length() > MAX_LENGTH) {
            throw new ProductNameInvalidException(
                    "El nombre del producto no puede tener más de " + MAX_LENGTH + " caracteres");
        }
    }
}
