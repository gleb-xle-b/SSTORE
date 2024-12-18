package org.example.store.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.store.Models.Product;
import org.example.store.database.DatabaseConnection;
import org.example.store.services.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MainController {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> idColumn;  // Столбец для ID
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;  // Столбец для описания
    @FXML
    private TableColumn<Product, Integer> supplierIdColumn;  // Столбец для ID поставщика
    @FXML
    private Label statusLabel;

    private ObservableList<Product> productList;
    private ProductService productService;
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    private Connection connection;
    {try {
        connection = DatabaseConnection.getConnection();
    } catch (
            SQLException e) {
        throw new RuntimeException(e);
    }}

    @FXML
    public void initialize() {
        ProductService productService = new ProductService(connection);
        // Привязываем колонки к свойствам модели
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        //categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        categoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(productService.getCategoryById(cellData.getValue().getCategoryId())));
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        supplierIdColumn.setCellValueFactory(cellData -> cellData.getValue().supplierIdProperty().asObject());

        // Инициализируем список продуктов
        productList = FXCollections.observableArrayList();

        //ProductService productService = new ProductService(connection);
        // Загрузка сотрудников из базы данных
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            showAlert("Ошибка", "Нет доступных продуктов для отображения.");
        } else {
            productList.addAll(products);
        }
        // Устанавливаем список товаров в таблицу
        productTable.setItems(productList);

        // Обновляем статус
        updateStatusLabel();
    }

    // Метод для добавления товара
    @FXML
    private void addProduct() {
        Product newProduct = showProductForm(null);  // null означает, что мы создаём новый товар
        if (newProduct != null) {
            productList.add(newProduct);
            updateStatusLabel();
            productTable.refresh();
        }
    }

    // Метод для удаления товара
    @FXML
    private void deleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            updateStatusLabel();
        } else {
            showAlert("Ошибка", "Не выбран товар для удаления!");
        }
    }

    // Метод для обновления товара
    @FXML
    private void updateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Product updatedProduct = showProductForm(selectedProduct);
            if (updatedProduct != null) {
                productTable.refresh();
            }
        } else {
            showAlert("Ошибка", "Не выбран товар для изменения!");
        }
    }

    // Обновление статуса (количества товаров)
    private void updateStatusLabel() {
        statusLabel.setText("Выбрано: " + productTable.getItems().size() + " товаров");
    }

    // Метод для отображения окна добавления/редактирования товара
    private Product showProductForm(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/store/product_form.fxml"));
            Parent parent = loader.load();

            // Получаем контроллер формы для товара
            ProductFormController controller = loader.getController();
            controller.setProduct(product);  // Передаем товар в форму (если редактируем)

            // Открываем окно
            Stage stage = new Stage();
            stage.setTitle("Добавить/Изменить товар");
            stage.setScene(new Scene(parent));
            stage.showAndWait();

            // Если пользователь сохранил товар, возвращаем объект продукта
            if (controller.isSaveClicked()) {
                return controller.getProduct();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть окно.");
        }
        return null;
    }



    // Метод для отображения информационных окон с сообщениями
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
