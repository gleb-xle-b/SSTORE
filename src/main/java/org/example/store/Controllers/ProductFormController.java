package org.example.store.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.store.Models.Product;

public class ProductFormController {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField supplierIdField;
    @FXML
    private Label errorLabel;

    private Product product;
    private boolean isSaveClicked = false;

    // Метод для установки значения product
    public void setProduct(Product product) {
        this.product = product;

        if (product != null) {
            // Устанавливаем значения полей из объекта product
            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
            categoryField.setText(product.getCategory());
            descriptionField.setText(product.getDescription());
            supplierIdField.setText(String.valueOf(product.getSupplierId()));
        }
    }

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    // Метод для сохранения изменений
    @FXML
    private void onSave() {
        try {
            // Проверка на пустые поля
            if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty() ||
                    categoryField.getText().isEmpty() || descriptionField.getText().isEmpty() || supplierIdField.getText().isEmpty()) {
                throw new IllegalArgumentException("Все поля должны быть заполнены.");
            }

            // Преобразование данных из текстовых полей
            double price = Double.parseDouble(priceField.getText());  // Преобразуем строку в число
            int quantity = Integer.parseInt(quantityField.getText());  // Преобразуем строку в целое число
            int supplierId = Integer.parseInt(supplierIdField.getText());  // Преобразуем строку в целое число

            // Обновляем или создаем новый товар
            if (product != null) {
                product.setName(nameField.getText());
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setCategory(categoryField.getText());
                product.setDescription(descriptionField.getText());
                product.setSupplierId(supplierId);
            } else {
                product = new Product(
                        Integer.parseInt(idField.getText()),  // ID товара
                        nameField.getText(),
                        price,
                        quantity,
                        categoryField.getText(),
                        descriptionField.getText(),
                        supplierId
                );
            }

            isSaveClicked = true;
            closeWindow();
        } catch (NumberFormatException e) {
            errorLabel.setText("Ошибка ввода данных. Пожалуйста, проверьте цену, количество и ID поставщика.");
            errorLabel.setVisible(true);
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    // Метод для отмены изменений
    @FXML
    private void onCancel() {
        // Запрашиваем у пользователя подтверждение на отмену
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Отмена");
        alert.setHeaderText("Вы уверены, что хотите отменить изменения?");
        alert.setContentText("Все несохраненные изменения будут потеряны.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            closeWindow();
        }
    }

    // Закрыть окно
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    // Возвращаем продукт
    public Product getProduct() {
        return product;
    }
}
