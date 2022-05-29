package com.example.exemple.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.exemple.model.Product;
import com.example.exemple.model.exception.ResourceNotFoundException;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository_old {

  private ArrayList<Product> products = new ArrayList<Product>();
  private Integer lastId = 0;

  /**
   * Method to return a products list
   * 
   * @return Product list
   */
  public List<Product> getAll() {
    return products;
  }

  /**
   * Method to return a product by id
   * 
   * @param id of the product for search
   * @return Product if found
   */
  public Optional<Product> getById(Integer id) {
    return products
        .stream()
        .filter(product -> product.getId() == id)
        .findFirst();
  }

  /**
   * Method to create a new product
   * 
   * @param product to create
   * @return Product created
   */
  public Product add(Product product) {

    lastId++;

    product.setId(lastId);
    products.add(product);

    return product;
  }

  /**
   * Method to update a product
   * 
   * @param product to update
   * @return Product updated
   */
  public Product update(Product product) {
    Optional<Product> productFound = getById(product.getId());

    if (productFound.isEmpty()) {
      throw new ResourceNotFoundException("Product not found");
    }

    delete(product.getId());

    products.add(product);

    return product;

  }

  /**
   * Method to update a product
   * 
   * @param product to update
   * @return Product updated
   */
  public void delete(Integer id) {
    products.removeIf(product -> product.getId() == id);
  }
}
