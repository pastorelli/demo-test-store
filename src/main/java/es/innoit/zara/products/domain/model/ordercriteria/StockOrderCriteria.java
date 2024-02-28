package es.innoit.zara.products.domain.model.ordercriteria;

import es.innoit.zara.products.domain.model.Product;

public record StockOrderCriteria(int orderWeight) implements OrderCriteria {

    public StockOrderCriteria(int orderWeight) {
        this.orderWeight = orderWeight;
    }

    @Override
    public int getOrderWeight(Product product) {
        return product.getTotalStockRemaining() * orderWeight;
    }
}
