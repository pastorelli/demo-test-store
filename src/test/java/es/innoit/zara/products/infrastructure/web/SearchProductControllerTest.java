package es.innoit.zara.products.infrastructure.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import es.innoit.zara.products.application.SearchProductService;
import es.innoit.zara.products.domain.model.Product;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;


@WebMvcTest(SearchProductController.class)
public class SearchProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchProductService searchProductService;

    @Test
    public void shouldReturnEmptyProductList() throws Exception {
        when(searchProductService.findAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/api/products")).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void shouldReturnProducts() throws Exception {
        Product p1 = new Product(new ProductId(1), new ProductName("p1"));
        Product p2 = new Product(new ProductId(2), new ProductName("p2"));
        List<Product> products = List.of(p1, p2);
        when(searchProductService.findAllProducts(any())).thenReturn(products);

        mockMvc.perform(get("/api/products")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("p1"))
                .andExpect(jsonPath("$[0].variants[0].size").value(ProductSize.SMALL.name()))
                .andExpect(jsonPath("$[0].variants[0].stock").value(0))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("p2"))
                .andExpect(jsonPath("$[1].variants[0].size").value(ProductSize.SMALL.name()))
                .andExpect(jsonPath("$[1].variants[0].stock").value(0));
    }

    @Test
    public void shouldReturnProductsWithCriteriaMocked() throws Exception {
        Product p1 = new Product(new ProductId(1), new ProductName("p1"));
        p1.incrementVariantStock(ProductSize.SMALL, new ProductStock(1));
        Product p2 = new Product(new ProductId(2), new ProductName("p2"));
        List<Product> products = List.of(p1, p2);
        when(searchProductService.findAllProducts(any())).thenReturn(products);

        mockMvc.perform(get("/api/products").param("StockOrderCriteria", "1")
                .param("SalesOrderCriteria", "1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("p1"))
                .andExpect(jsonPath("$[0].variants[0].size").value(ProductSize.SMALL.name()))
                .andExpect(jsonPath("$[0].variants[0].stock").value(1))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("p2"))
                .andExpect(jsonPath("$[1].variants[0].size").value(ProductSize.SMALL.name()))
                .andExpect(jsonPath("$[1].variants[0].stock").value(0));
    }
}
