package com.example.lab_test1.product.dto;

public class ProductDTO {
  private String name;
  private Integer quantity;

  public ProductDTO(String name, Integer quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public ProductDTO() {
  }

  public String getName() {
    return name;
  }

  public Integer getQuantity() {
    return quantity;
  }
}
