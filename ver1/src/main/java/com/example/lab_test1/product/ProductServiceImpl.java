package com.example.lab_test1.product;

import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;

import com.example.lab_test1.product.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductRepository productRepository;

  public Page<Product> listProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  public Product createProduct(ProductDTO dto) throws Exception {
    var oldProduct = productRepository.findByName(dto.getName());
    if (oldProduct.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already existed with name:" + dto.getName());
    }

    return productRepository.save(Product.builder()
        .name(dto.getName())
        .quantity(dto.getQuantity())
        .build());
  }

  public Product updateProduct(Long id, ProductDTO dto) throws Exception {
    var otherProduct = productRepository.findByName(dto.getName());
    if (otherProduct.isPresent() && otherProduct.get().getId() != id) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already existed with name:" + dto.getName());
    }

    var curProduct = productRepository.findById(id);
    if (!curProduct.isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id:" + id);

    var pr = curProduct.get();
    pr.setName(dto.getName());
    pr.setQuantity(dto.getQuantity());
    return productRepository.save(pr);
  }

  public void deleteProduct(Long id) throws Exception {
    var curProduct = productRepository.findById(id);
    if (!curProduct.isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id:" + id);

    productRepository.deleteById(id);
  }
}
