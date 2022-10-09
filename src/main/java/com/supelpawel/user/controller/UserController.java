package com.supelpawel.user.controller;

import com.supelpawel.role.data.Role;
import com.supelpawel.role.service.RoleService;
import com.supelpawel.user.model.User;
import com.supelpawel.user.service.UserService;
import java.util.Collection;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;
  private final RoleService roleService;

  @ModelAttribute("roles")
  Collection<Role> findAllRoles() {
    return roleService.findAll();
  }

  @GetMapping("/user/add")
  String showAddUserForm(Model model) {
    return userService.showAddUserForm(model);
  }

  @PostMapping("/user/add")
  String processAddUserForm(@Valid @ModelAttribute("user") User user, BindingResult result) {
    return userService.processAddUserForm(user, result);
  }
}