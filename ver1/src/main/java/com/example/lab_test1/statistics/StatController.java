package com.example.lab_test1.statistics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stat")
public class StatController {
  @GetMapping
  public String getStat(){
    return "statistics for admin!";
  }
}
