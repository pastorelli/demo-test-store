package es.innoit.zara.products.domain.model;

import es.innoit.zara.products.domain.exceptions.InvalidProductStockException;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;

public class ProductSizeVariant {
    private ProductSize size;
    private ProductStock stock;
    private ProductStock unitsSold;

    public ProductSizeVariant(ProductSize size) {
        this.size = size;
        this.stock = new ProductStock(0);
        this.unitsSold = new ProductStock(0);
    }

    public ProductSizeVariant(ProductSize size, ProductStock stock, ProductStock unitsSold) {
        this.size = size;
        this.stock = stock;
        this.unitsSold = unitsSold;
    }

    public void incrementStock(ProductStock stock) {
        this.stock = new ProductStock(this.stock.amount() + stock.amount());
    }

    public void decrementStock(ProductStock stock) {
        this.stock = new ProductStock(this.stock.amount() - stock.amount());
    }

    public void sellProductVariant(ProductStock amount) {
        if (amount.amount() > this.stock.amount()) {
            throw new InvalidProductStockException(
                    "No hay suficiente stock para vender: " + amount.amount());
        }
        this.decrementStock(amount);
        this.unitsSold = new ProductStock(this.unitsSold.amount() + amount.amount());
    }

    public int getUnitsSold() {
        return unitsSold.amount();
    }

    public ProductStock getStock() {
        return stock;
    }

    public ProductSize getSize() {
        return size;
    }

    public boolean compareSize(ProductSize size) {
        return this.size.equals(size);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductSizeVariant) {
            ProductSizeVariant other = (ProductSizeVariant) obj;
            return this.size.equals(other.size);
        }
        return false;
    }
}
