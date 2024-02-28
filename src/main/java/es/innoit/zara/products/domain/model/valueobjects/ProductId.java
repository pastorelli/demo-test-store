package es.innoit.zara.products.domain.model.valueobjects;

public record ProductId(int id) {
    public ProductId {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El id del producto no puede ser menor o igual a cero: " + id);
        }
    }
}
