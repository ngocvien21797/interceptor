package com.example.demo.web;

import com.example.demo.security.RequireRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @RequireRole("ADMIN")
  @GetMapping("/admin/dashboard")
  public String admin() {
    return "admin-dashboard";
  }
}
