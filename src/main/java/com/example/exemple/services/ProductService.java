package com.example.exemple.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.exemple.model.Product;
import com.example.exemple.model.exception.ResourceNotFoundException;
import com.example.exemple.repository.ProductRepository;
import com.example.exemple.shared.ProductDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  /**
   * Method to return a products list
   * 
   * @return Product list
   */
  public List<ProductDTO> getAll() {
    List<Product> products = productRepository.findAll();

    return products
        .stream()
        .map(product -> new ModelMapper().map(product, ProductDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Method to return a product by id
   * 
   * @param id of the product for search
   * @return Product if found
   */
  public Optional<ProductDTO> getById(Integer id) {
    Optional<Product> product = productRepository.findById(id);

    if (product.isEmpty()) {
      throw new ResourceNotFoundException("Product id: " + id + " not found");
    }

    ProductDTO productDTO = new ModelMapper().map(product.get(), ProductDTO.class);

    return Optional.of(productDTO);

  }

  /**
   * Method to create a new product
   * 
   * @param product to create
   * @return Product created
   */
  public ProductDTO add(ProductDTO productDTO) {
    productDTO.setId(null);

    ModelMapper mapper = new ModelMapper();

    Product product = mapper.map(productDTO, Product.class);

    product = productRepository.save(product);

    productDTO.setId(product.getId());

    return productDTO;
  }

  /**
   * Method to update a product
   * 
   * @param product to update
   * @return Product updated
   */
  public ProductDTO update(Integer id, ProductDTO productDTO) {
    Optional<Product> productExists = productRepository.findById(id);

    if (productExists.isEmpty()) {
      throw new ResourceNotFoundException("Product id: " + id + " not found");
    }

    productDTO.setId(id);

    ModelMapper mapper = new ModelMapper();

    Product product = mapper.map(productDTO, Product.class);

    product = productRepository.save(product);

    return productDTO;
  }

  /**
   * Method to delete a product
   * 
   * @param id of the product to delete
   * @return Product deleted
   */
  public void delete(Integer id) {
    Optional<Product> product = productRepository.findById(id);

    if (product.isEmpty()) {
      throw new ResourceNotFoundException("Product id: " + id + " not found");
    }

    productRepository.deleteById(id);
  }
}
