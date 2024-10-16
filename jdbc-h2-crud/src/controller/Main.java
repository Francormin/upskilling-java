package controller;

import dao.ProductDao;
import dao.dto.ProductDto;
import dao.impl.ProductDaoImplH2;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoImplH2();

        // productDao.createProductTable();
        // productDao.alterPriceColumnType();

        ProductDto productDtoToInsert = new ProductDto("Motorola Moto X", 149.9);
        productDao.insert(productDtoToInsert);

        ProductDto productDtoToUpdate = new ProductDto("Samsung Galaxy S13", 199.9);
        productDao.update(8, productDtoToUpdate);

        productDao.delete(4);

        System.out.println("\nLista de productos almacenados en la base de datos:\n");
        List<ProductDto> products = productDao.getAll();
        for (ProductDto productDto : products) {
            System.out.println("Nombre: " + productDto.getName());
            System.out.println("Precio: " + productDto.getPrice());
            System.out.println("---------------------------");
        }
    }
}