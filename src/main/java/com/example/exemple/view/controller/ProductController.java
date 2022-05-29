package com.example.exemple.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.exemple.services.ProductService;
import com.example.exemple.shared.ProductDTO;
import com.example.exemple.view.model.ProductRequest;
import com.example.exemple.view.model.ProductResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAll() {
    List<ProductDTO> products = productService.getAll();

    ModelMapper mapper = new ModelMapper();

    List<ProductResponse> response = products.stream()
        .map(product -> mapper
            .map(product, ProductResponse.class))
        .collect(Collectors.toList());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<ProductResponse>> getById(
      @PathVariable Integer id) {
    try {
      Optional<ProductDTO> productDTO = productService.getById(id);

      ProductResponse product = new ModelMapper()
          .map(productDTO.get(), ProductResponse.class);

      return new ResponseEntity<>(Optional.of(product), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping
  public ResponseEntity<ProductResponse> add(
      @RequestBody ProductRequest productRequest) {
    ModelMapper mapper = new ModelMapper();

    ProductDTO productDTO = mapper
        .map(productRequest, ProductDTO.class);

    productDTO = productService.add(productDTO);

    return new ResponseEntity<>(mapper
        .map(
            productDTO, ProductResponse.class),
        HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(
      @RequestBody ProductRequest productRequest, @PathVariable Integer id) {
    ModelMapper mapper = new ModelMapper();

    ProductDTO productDTO = mapper
        .map(productRequest, ProductDTO.class);

    productDTO = productService.update(id, productDTO);

    return new ResponseEntity<>(
        mapper.map(productDTO, ProductResponse.class),
        HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    productService.delete(id);
    return new ResponseEntity<>(
        "Product id:" + id + " deleted",
        HttpStatus.NO_CONTENT);
  }
}
