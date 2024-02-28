package es.innoit.zara.products.infrastructure.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.innoit.zara.products.application.SearchProductUseCase;
import es.innoit.zara.products.domain.model.ordercriteria.OrderCriteria;
import es.innoit.zara.products.domain.model.ordercriteria.SalesOrderCriteria;
import es.innoit.zara.products.domain.model.ordercriteria.StockOrderCriteria;
import es.innoit.zara.products.infrastructure.web.dto.ProductDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "API para obtener el listado de productos", version = "1.0",
                description = "API para obtener el listado de productos."),
        servers = @Server(url = "http://localhost:8080"))
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class SearchProductController {

    private final SearchProductUseCase searchProductUseCase;

    @Operation(summary = "Listar los productos",
            description = "Lista todos los productos disponibles en el sistema, "
                    + "ordenados por stock y/o ventas")
    @GetMapping
    public List<ProductDTO> findAllProducts(
            @Parameter(description = "Criterio de ordenamiento por stock") @RequestParam(
                    required = false, defaultValue = "0") Integer StockOrderCriteria,
            @Parameter(description = "Criterio de ordenamiento por ventas") @RequestParam(
                    required = false, defaultValue = "0") Integer SalesOrderCriteria) {
        List<OrderCriteria> orderCriteria = List.of(new StockOrderCriteria(StockOrderCriteria),
                new SalesOrderCriteria(SalesOrderCriteria));
        return searchProductUseCase.findAllProducts(orderCriteria).stream()
                .map(ProductDTO::fromDomain).toList();
    }
}
