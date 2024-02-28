package es.innoit.zara.products.infrastructure.web.dto;

import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSizeVariantDTO {

    @Schema(description = "Talla del producto", oneOf = ProductSize.class)
    String size;

    @Schema(description = "Cantidad de unidades en stock")
    int stock;
}
