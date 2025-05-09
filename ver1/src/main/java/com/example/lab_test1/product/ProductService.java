package com.example.lab_test1.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.lab_test1.product.dto.ProductDTO;

public interface ProductService {
  public Page<Product> listProducts(Pageable pageable) ;

  public Product createProduct(ProductDTO dto) throws Exception;

  public Product updateProduct(Long id, ProductDTO dto) throws Exception;

  public void deleteProduct(Long id) throws Exception;
}
