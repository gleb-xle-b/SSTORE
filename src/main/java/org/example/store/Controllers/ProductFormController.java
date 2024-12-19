package org.example.store.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.store.Models.Product;
import org.example.store.database.DatabaseConnection;
import org.example.store.services.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductFormController extends MainController {

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
    private Connection connection;
    {try {
        connection = DatabaseConnection.getConnection();
    } catch (
    SQLException e) {
        throw new RuntimeException(e);
    }}
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    // Метод для установки значения product
    public void setProduct(Product product) {
        this.product = product;

        if (product != null) {
            // Устанавливаем значения полей из объекта product
            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
            categoryField.setText(productService.getCategoryById(product.getCategoryId()));
            descriptionField.setText(product.getDescription());
            supplierIdField.setText(String.valueOf(product.getSupplierId()));
        }
    }

    public boolean isSaveClicked() {
        return isSaveClicked;
    }


    /*name, description, price, quantity, categoryId, supplierId*/

    @FXML
    private void loadProductDataForEditing() {
        Product selectedProduct = MainController.productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Заполняем текстовые поля
            nameField.setText(selectedProduct.getName());
            descriptionField.setText(selectedProduct.getDescription());
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
            quantityField.setText(String.valueOf(selectedProduct.getQuantity()));
            categoryField.setText(String.valueOf(productService.getCategoryById(selectedProduct.getCategoryId())));
            supplierIdField.setText(String.valueOf(selectedProduct.getSupplierId()));
            //hoursWorkedField.setText(String.valueOf(selectedEmployee.getHoursWorked())); // Поле часов работы

            // Заполняем ComboBox для должности и выбираем текущую
            /*List<String> positions = employeeService.getAllPositions();
            positionComboBox.getItems().setAll(positions);
            positionComboBox.setValue(employeeService.getPositionById(selectedEmployee.getPositionId()));*/

            // Заполняем ComboBox для отдела и выбираем текущий
            /*List<String> departments = employeeService.getAllDepartments();
            departmentComboBox.getItems().setAll(departments);
            departmentComboBox.setValue(employeeService.getDepartmentById(selectedEmployee.getDepartmentId()));*/

        } else {
            showAlert("Ошибка", "Выберите сотрудника для редактирования.");
        }
    }



    @FXML
    private void handleSaveProductChanges() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                /*name, description, price, quantity, categoryId, supplierId*/

                // Получаем данные из текстовых полей
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String category = categoryField.getText();
                int supplierId = Integer.parseInt(supplierIdField.getText());

                // Получаем ID должности и отдела
                int categoryId = productService.getCategoryIdByName(category);

                // Обновляем данные сотрудника
                selectedProduct.setName(name);
                selectedProduct.setDescription(description);
                selectedProduct.setPrice(price);
                selectedProduct.setQuantity(quantity);
                selectedProduct.setCategoryId(categoryId);
                selectedProduct.setSupplierId(supplierId);

                // Обновляем данные в базе
                productService.updateProduct(selectedProduct);

                // Обновляем таблицу сотрудников
                productService.updateProduct(selectedProduct);

                // Очищаем все поля
                nameField.clear();
                descriptionField.clear();
                priceField.clear();
                quantityField.clear();
                categoryField.clear();
                supplierIdField.clear();
                showAlert("Успех", "Данные о продукте успешно обновлены.");
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Некорректный ввод данных.");
            }
        } else {
            showAlert("Ошибка", "Выберите сотрудника для сохранения изменений.");
        }
        //originalEmployeeList = new ArrayList<>(employeeTable.getItems());
    }

    // Метод для сохранения изменений
    /*@FXML
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
                product.setCategoryId(productService.getCategoryIdByName(categoryField.getText()));
                product.setDescription(descriptionField.getText());
                product.setSupplierId(supplierId);
            } else {
                product = new Product(
                        Integer.parseInt(idField.getText()),  // ID товара
                        nameField.getText(),
                        descriptionField.getText(),
                        price,
                        quantity,
                        productService.getCategoryIdByName(categoryField.getText()),
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
    }*/


    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    private void onSave() {
        ProductService productService = new ProductService(connection);
        if (!(nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty() ||
                categoryField.getText().isEmpty() || descriptionField.getText().isEmpty() || supplierIdField.getText().isEmpty())) {
            try {
                // Преобразуем данные из текстовых полей
                String name = nameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                int categoryId = productService.getCategoryIdByName(categoryField.getText());
                int supplierId = Integer.parseInt(supplierIdField.getText());

                // Создаем новый продукт
                Product newProduct = new Product(
                        name,
                        description,
                        price,
                        quantity,
                        categoryId,
                        supplierId
                );

                // Добавляем продукт в базу данных
                productService.addProduct(newProduct);

                // Добавляем продукт в локальный список и обновляем таблицу
                if (productList != null) {
                    productList.add(newProduct); // Обновляем общий список
                }


                isSaveClicked = true;
                closeWindow();

            } catch (NumberFormatException e) {
                errorLabel.setText("Ошибка ввода данных. Пожалуйста, проверьте цену, количество и ID поставщика.");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Заполните все поля");
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
