package com.example.shoppingmall.controller;

import com.example.shoppingmall.model.User;
import com.example.shoppingmall.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // 입력 데이터 검증
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("모든 필드를 입력해야 합니다.");
        }

        userService.registerUser(user);
        return "User registered successfully!";
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 로그인 기능 추가
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.login(username, password);

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("message", "Login successful");
            response.put("userId", user.getId());
        } else {
            response.put("message", "Invalid username or password");
        }
        return response;
    }


}


