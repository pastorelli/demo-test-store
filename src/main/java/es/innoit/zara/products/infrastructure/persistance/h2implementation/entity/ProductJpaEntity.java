package es.innoit.zara.products.infrastructure.persistance.h2implementation.entity;

import java.util.List;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductJpaEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductVariantJpaEntity> variants;

    public static Product toDomain(ProductJpaEntity entity) {
        Product product = new Product(new ProductId(entity.getId()),
                new ProductName(entity.getName()), List.of());
        entity.getVariants().stream().forEach(variant -> {
            product.addVariant(ProductSize.valueOf(variant.getSize()),
                    new ProductStock(variant.getStock()), new ProductStock(variant.getUnitsSold()));
        });
        return product;
    }
}
