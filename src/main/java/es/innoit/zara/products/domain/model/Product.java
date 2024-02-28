package es.innoit.zara.products.domain.model;

import java.util.LinkedList;
import java.util.List;
import es.innoit.zara.products.domain.exceptions.ProductSizeVariantAlreadyExistsException;
import es.innoit.zara.products.domain.exceptions.ProductSizeVariantDoesNotExistException;
import es.innoit.zara.products.domain.model.valueobjects.ProductId;
import es.innoit.zara.products.domain.model.valueobjects.ProductName;
import es.innoit.zara.products.domain.model.valueobjects.ProductSize;
import es.innoit.zara.products.domain.model.valueobjects.ProductStock;

public class Product {
    private ProductId id;
    private ProductName name;
    private List<ProductSizeVariant> variants = new LinkedList<>();

    public Product(ProductId id, ProductName name) {
        this.id = id;
        this.name = name;
        this.variants.addAll(
                List.of(ProductSize.values()).stream().map(ProductSizeVariant::new).toList());
    }

    public Product(ProductId id, ProductName name, ProductSize size) {
        this.id = id;
        this.name = name;
        checkIfVariantExists(size);
        this.variants.add(new ProductSizeVariant(size));
    }

    public Product(ProductId id, ProductName name, List<ProductSize> productSizes) {
        this.id = id;
        this.name = name;

        productSizes.stream().forEach(size -> {
            checkIfVariantExists(size);
        });

        this.variants.addAll(productSizes.stream().map(ProductSizeVariant::new).toList());
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name.Name();
    }

    public int getVariantsSize() {
        return variants.size();
    }

    public boolean variantExists(ProductSize size) {
        return variants.stream().anyMatch(variant -> variant.compareSize(size));
    }

    public void addVariant(ProductSize size) {
        checkIfVariantExists(size);
        this.variants.add(new ProductSizeVariant(size));
    }

    public void addVariant(ProductSize size, ProductStock stock, ProductStock unitsSold) {
        checkIfVariantExists(size);
        this.variants.add(new ProductSizeVariant(size, stock, unitsSold));
    }

    public void removeVariant(ProductSize size) {
        checkIfVariantDoesNotExists(size);
        this.variants.removeIf(variant -> variant.compareSize(size));
    }

    public List<ProductSize> getVariantsNames() {
        return variants.stream().map(ProductSizeVariant::getSize).toList();
    }

    public ProductStock getStock(ProductSize size) {
        return variants.stream().filter(variant -> variant.compareSize(size)).findFirst().get()
                .getStock();
    }

    public void incrementVariantStock(ProductSize size, ProductStock stock) {
        checkIfVariantDoesNotExists(size);
        variants.stream().filter(variant -> variant.compareSize(size)).findFirst().get()
                .incrementStock(stock);
    }

    public void decrementVariantStock(ProductSize size, ProductStock stock) {
        checkIfVariantDoesNotExists(size);
        variants.stream().filter(variant -> variant.compareSize(size)).findFirst().get()
                .decrementStock(stock);
    }

    public int getTotalStockRemaining() {
        return variants.stream().mapToInt(variant -> variant.getStock().amount()).sum();
    }

    public void sellProduct(ProductSize size, ProductStock amount) {
        checkIfVariantDoesNotExists(size);
        variants.stream().filter(variant -> variant.compareSize(size)).findFirst().get()
                .sellProductVariant(amount);
    }

    public int getUnitsSold(ProductSize small) {
        return variants.stream().filter(variant -> variant.compareSize(small)).findFirst().get()
                .getUnitsSold();
    }

    public int getTotalUnitsSold() {
        return variants.stream().mapToInt(ProductSizeVariant::getUnitsSold).sum();
    }

    private void checkIfVariantExists(ProductSize sizeVariant) {
        if (variantExists(sizeVariant)) {
            throw new ProductSizeVariantAlreadyExistsException("El producto " + this.getName()
                    + " ya tiene una variante con el nombre " + sizeVariant);
        }
    }

    private void checkIfVariantDoesNotExists(ProductSize sizeVariant) {
        if (!variantExists(sizeVariant)) {
            throw new ProductSizeVariantDoesNotExistException(
                    "El producto no tiene una variante con el nombre " + sizeVariant);
        }
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;
        return this.id.equals(other.id);
    }
}
