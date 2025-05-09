package com.example.lab_test1.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ProductDTO {
  @NotBlank
  @Pattern(regexp = "^[A-Za-z0-9 ]*$", message = "name must only contains leter, space and number")
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
