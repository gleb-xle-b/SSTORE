package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Models.Product;
import javafx.stage.Stage;

//import com.example.universitytest.models.Employee;

public class MainController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private Label statusLabel;

    private ObservableList<Product> productList;

    @FXML
    public void initialize() {
        // Создаем тестовые данные
        productList = FXCollections.observableArrayList(
                new Product("Apple", 1.0, 50, "Fruits"),
                new Product("Milk", 1.5, 30, "Dairy"),
                new Product("Bread", 2.0, 20, "Bakery")
        );

        // Привязываем колонки к свойствам модели
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        // Добавляем данные в таблицу
        productTable.setItems(productList);

        // Обновляем статус
        updateStatusLabel();
    }

    @FXML
    private void addProduct() {
        Product newProduct = showProductForm(null);
        if (newProduct != null) {
            productList.add(newProduct);
            updateStatusLabel();
        }
    }

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

    private void updateStatusLabel() {
        statusLabel.setText("Выбрано: " + productTable.getItems().size() + " товаров");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Product showProductForm(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/store/product_form.fxml"));
            Parent parent = loader.load();

            ProductFormController controller = loader.getController();
            controller.setProduct(product);

            Stage stage = new Stage();
            stage.setTitle("Добавить/Изменить товар");
            stage.setScene(new Scene(parent));
            stage.showAndWait();

            if (controller.isSaveClicked()) {
                return controller.getProduct();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть окно.");
        }
        return null;
    }

}
