package es.innoit.zara.products.domain.model.ordercriteria;

import es.innoit.zara.products.domain.model.Product;

public interface OrderCriteria {
    int getOrderWeight(Product product);
}
