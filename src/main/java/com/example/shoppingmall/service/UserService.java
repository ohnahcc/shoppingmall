package com.example.shoppingmall.service;

import com.example.shoppingmall.mapper.UserMapper;
import com.example.shoppingmall.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void registerUser(User user) {
        userMapper.insertUser(user);
    }

    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    // 로그인 기능 추가
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null; // 사용자가 없거나 비밀번호가 틀림
        }
        return user; // 로그인 성공
    }
}

