package com.example.test2.stat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/stat")
@SecurityRequirement(name = "bearerAuth")
public class StatController {
  @GetMapping
  public String getStat(){
    return "send stat";
  }
}
