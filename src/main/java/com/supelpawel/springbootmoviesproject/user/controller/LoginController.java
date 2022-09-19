package com.supelpawel.springbootmoviesproject.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/login")
  String login() {
    return "auth/login";
  }
}
