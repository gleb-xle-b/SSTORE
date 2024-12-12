package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Product {
    private StringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty quantity;
    private StringProperty category;

    // Конструктор
    public Product(String name, double price, int quantity, String category) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.category = new SimpleStringProperty(category);
    }

    // Геттеры и сеттеры для каждого свойства
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    // Методы свойств
    public StringProperty nameProperty() {
        return name;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    // Метод для удобного отображения информации о товаре
    @Override
    public String toString() {
        return String.format("%s (%s): $%.2f, %d pcs", name.get(), category.get(), price.get(), quantity.get());
    }
}
