package dao.impl;

import config.JdbcConfiguration;
import dao.ProductDao;
import dao.dto.ProductDto;
import entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImplH2 implements ProductDao {
    private final Connection connection;

    public ProductDaoImplH2() {
        this.connection = JdbcConfiguration.getDBConnection();
    }

    /*
    public void createProductTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "CREATE TABLE product(" +
                            "id INT AUTO_INCREMENT," +
                            "name VARCHAR(50) NOT NULL," +
                            "price INT)"
            );
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            System.out.println("Tabla 'product' creada exitosamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */

    /*
    public void alterPriceColumnType() {
        try {
            String sql = "ALTER TABLE product ALTER COLUMN price DOUBLE";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            System.out.println("Tipo de columna 'precio', de la tabla 'product', modificado exitosamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */

    @Override
    public void insert(ProductDto productDto) {
        Product newProduct = new Product();
        newProduct.setName(productDto.getName());
        newProduct.setPrice(productDto.getPrice());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO product (name, price) VALUES (?, ?)"
            );

            preparedStatement.setString(1, newProduct.getName());
            preparedStatement.setDouble(2, newProduct.getPrice());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            System.out.println("Producto insertado exitosamente.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productsDto = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM product"
            );

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                ProductDto productDto = new ProductDto(name, price);
                productsDto.add(productDto);
            }

            resultSet.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsDto;
    }

    @Override
    public void update(int id, ProductDto productDto) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE product SET name = ?, price = ? WHERE id = ?"
            );

            ps.setString(1, productDto.getName());
            ps.setDouble(2, productDto.getPrice());
            ps.setInt(3, id);
            int intReturned = ps.executeUpdate();

            ps.close();
            if (intReturned == 1) {
                System.out.println("Producto con ID " + id + " actualizado exitosamente.");
            } else {
                System.out.println("No existe producto con ID " + id + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int productId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM product WHERE id = ?"
            );

            ps.setInt(1, productId);
            int intReturned = ps.executeUpdate();

            ps.close();
            if (intReturned == 1) {
                System.out.println("Producto con ID " + productId + " borrado exitosamente.");
            } else {
                System.out.println("No existe producto con ID " + productId + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}