package org.example.store.services;

import org.example.store.Models.Product;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ProductService {
    private Connection connection;

    public ProductService(Connection connection) {
        this.connection = connection;
    }

    // Получение всех продуктов из базы данных
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM productsSELECT * FROM products";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Извлекаем данные из ResultSet и создаем объекты Product
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                int categoryId = resultSet.getInt("category_id");  // Исправляем на categoryId
                int supplierId = resultSet.getInt("supplier_id");

                Product product = new Product(productId, name, description, price, quantity, categoryId, supplierId);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }



    // Добавление нового продукта в базу данных
    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (name, description, price, quantity, category_id, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategoryId());  // Используем categoryId
            preparedStatement.setInt(6, product.getSupplierId());  // Используем supplierId

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCategoryById(int categoryId) {
        String query = "SELECT name FROM categories WHERE category_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Выведите исключение для отладки
        }
        return "Неизвестная категория"; // Если должность не найдена
    }


    public int getCategoryIdByName(String categoryName) {
        String query = "SELECT category_id FROM categories WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Обновление данных продукта в базе данных
    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ?, category_id = ?, supplier_id = ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setInt(5, product.getCategoryId());  // Используем categoryId
            preparedStatement.setInt(6, product.getSupplierId());  // Используем supplierId
            preparedStatement.setInt(7, product.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение категории по categoryId (если нужно название категории)
    public String getCategoryNameById(int categoryId) {
        String query = "SELECT name FROM categories WHERE category_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");  // Возвращаем название категории
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение поставщика по supplierId (если нужно название поставщика)
    public String getSupplierNameById(int supplierId) {
        String query = "SELECT name FROM suppliers WHERE supplier_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplierId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");  // Возвращаем название поставщика
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
