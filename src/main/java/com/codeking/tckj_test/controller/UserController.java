package com.codeking.tckj_test.controller;

import com.codeking.tckj_test.entity.User;
import com.codeking.tckj_test.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String redirectToUsers() {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/users/details/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-details";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user) {
        int i = userService.updateUser(id, user);
        if (i > 0) {
            return "redirect:/users";
        } else {
            // 失败 抛出
            throw new RuntimeException("Update failed.");
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-create";
    }

    @PostMapping("/users/create")
    public String createUserSubmit(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }


    // 根据用户名或手机尾号（后4位）精准查询用户
    @GetMapping("/users/search")
    public ResponseEntity<?> searchUsers(@RequestParam("keyword") String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return ResponseEntity.badRequest().body("Keyword cannot be empty");
        }

        // 判断是否为手机号码后4位
        boolean isPhoneNumberSuffix = keyword.matches("\\d{4}");
        List<User> users;

        if (isPhoneNumberSuffix) {
            // 根据手机尾号查询用户
            users = userService.getUsersByPhoneNumberSuffix(keyword);
        } else {
            // 根据用户名查询用户
            User user = userService.getUserByName(keyword);
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            users = Collections.singletonList(user);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/ranking")
    public ResponseEntity<?> getUserByRanking(@RequestParam("rank") int rank) {
        if (rank <= 0) {
            return ResponseEntity.badRequest().body("Invalid ranking");
        }

        List<User> usersByRank = userService.getUsersByRank(rank);
        if (usersByRank == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usersByRank);
    }
}
