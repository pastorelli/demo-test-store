package es.innoit.zara.products.domain.model.ordercriteria;

import es.innoit.zara.products.domain.model.Product;

public record SalesOrderCriteria(int orderWeight) implements OrderCriteria {

    public SalesOrderCriteria(int orderWeight) {
        this.orderWeight = orderWeight;
    }

    @Override
    public int getOrderWeight(Product product) {
        return product.getTotalUnitsSold() * orderWeight;
    }

}
