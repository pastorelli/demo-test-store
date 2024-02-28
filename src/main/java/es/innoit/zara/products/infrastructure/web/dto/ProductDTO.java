package es.innoit.zara.products.infrastructure.web.dto;

import java.util.List;
import java.util.stream.Stream;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

        @Schema(description = "Identificador del producto")
        private int id;

        @Schema(description = "Nombre del producto")
        private String name;

        @Schema(description = "Cantidad total de unidades vendidas")
        private int unitsSold;

        @Schema(description = "Variantes del producto")
        private List<ProductSizeVariantDTO> variants;

        public static ProductDTO fromDomain(Product product) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.id = product.getId().id();
                productDTO.name = product.getName();
                productDTO.unitsSold = product.getTotalUnitsSold();
                productDTO.variants = Stream.of(ProductSize.values())
                                .map(size -> product.variantExists(size)
                                                ? new ProductSizeVariantDTO(size.name(),
                                                                product.getStock(size).amount())
                                                : new ProductSizeVariantDTO(size.name(), 0))
                                .toList();
                return productDTO;
        }
}
