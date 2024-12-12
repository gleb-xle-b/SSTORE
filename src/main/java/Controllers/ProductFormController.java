package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Models.Product;

public class ProductFormController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;

    private Product product;
    private boolean isSaveClicked = false;

    public void setProduct(Product product) {
        this.product = product;

        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
            categoryField.setText(product.getCategory());
        }
    }

    public boolean isSaveClicked() {
        return isSaveClicked;
    }

    @FXML
    private void onSave() {
        if (product != null) {
            product.setName(nameField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setQuantity(Integer.parseInt(quantityField.getText()));
            product.setCategory(categoryField.getText());
        } else {
            product = new Product(
                    nameField.getText(),
                    Double.parseDouble(priceField.getText()),
                    Integer.parseInt(quantityField.getText()),
                    categoryField.getText()
            );
        }

        isSaveClicked = true;
        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public Product getProduct() {
        return product;
    }
}
