package com.example.shoppingmall.mapper;

import com.example.shoppingmall.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (username, password, email) VALUES (#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(int id);

    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    // 📌 로그인 시 username으로 회원 정보 조회
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
}

