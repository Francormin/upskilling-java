package dao;

import dao.dto.ProductDto;

import java.util.List;

public interface ProductDao {
    // CRUD

    // CREATE
    // void createProductTable();
    // void alterPriceColumnType();

    void insert(ProductDto productDto);

    // READ
    List<ProductDto> getAll();

    // UPDATE
    void update(int id, ProductDto productDto);

    // DELETE
    void delete(int productId);
}