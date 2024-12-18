package org.example.store.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty quantity;
    private IntegerProperty categoryId;
    private StringProperty description;  // Новое поле для описания товара
    private IntegerProperty supplierId;  // Новое поле для ID поставщика

    // Конструктор
    public Product(int id, String name, String description, double price, int quantity, int categoryId, int supplierId) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.categoryId = new SimpleIntegerProperty(categoryId);
        this.description = new SimpleStringProperty(description);  // Инициализация нового поля
        this.supplierId = new SimpleIntegerProperty(supplierId);  // Инициализация нового поля
    }

    // Геттеры и сеттеры для свойств
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }



    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public IntegerProperty categoryIdProperty() {
        return categoryId;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    // Дополнительные методы, если нужно

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id.get() +
                ", name='" + name.get() + '\'' +
                ", price=" + price.get() +
                ", quantity=" + quantity.get() +
                ", category='" + categoryId.get() + '\'' +
                ", description='" + description.get() + '\'' +
                ", supplierId=" + supplierId.get() +
                '}';
    }

}
