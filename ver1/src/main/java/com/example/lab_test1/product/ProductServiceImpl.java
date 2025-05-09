package com.example.lab_test1.product;

import javax.naming.NameNotFoundException;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lab_test1.product.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  private ProductRepository productRepository;

  public Page<Product> listProducts(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  public Product getProduct(Long id) throws Exception {
    return productRepository.findById(id)
        .orElseThrow(() -> new NameNotFoundException("Product not found with id" + id));
  }

  public Product createProduct(ProductDTO dto) throws Exception{
    var pr = productRepository.findByName(dto.getName()).orElseGet(() -> null);
    if (pr != null) {
      throw new BadRequestException("Product already existed with name:" + dto.getName());
    }

    return productRepository.save(Product.builder()
        .name(dto.getName())
        .quantity(dto.getQuantity())
        .build());
  }

  public Product updateProduct(Long id, ProductDTO dto) throws Exception {
    var oldPr = productRepository.findByName(dto.getName()).orElseGet(() -> null);
    if (oldPr != null) {
      throw new BadRequestException("Product already existed with name:" + dto.getName());
    }

    var pr = getProduct(id);
    pr.setName(dto.getName());
    pr.setQuantity(dto.getQuantity());
    return productRepository.save(pr);
  }

  public void deleteProduct(Long id) throws Exception {
    var pr = getProduct(id);
    productRepository.deleteById(id);
  }
}
