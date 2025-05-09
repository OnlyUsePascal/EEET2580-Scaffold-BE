package com.example.lab_test1.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lab_test1.product.dto.ProductDTO;

@RestController
@RequestMapping("/product")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping
  public Page<Product> listProducts(
      @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
      @RequestParam(name = "pageNo", defaultValue = "0") int pageNo) {

    var pageable = PageRequest.of(pageNo, 4, Sort.by(sortBy));
    return productService.listProducts(pageable);
  }

  @PostMapping
  public Product addProduct(@RequestBody ProductDTO dto) throws Exception {
    return productService.createProduct(dto);
  }

  @PutMapping("/{id}")
  public Product updateProduct(
      @PathVariable Long id,
      @RequestBody ProductDTO dto) throws Exception {
    return productService.updateProduct(id, dto);
  }

  @DeleteMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) throws Exception {
    productService.deleteProduct(id);
    return "deleted product with id:" + id;
  }
}
