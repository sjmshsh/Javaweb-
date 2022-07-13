package com.example.mybatisplustest.mapper;

import com.example.mybatisplustest.UserDao.Userdao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author 写你的名字
 * @Date 2022/7/13 14:13 （可以根据需要修改）
 * @Version 1.0 （版本号）
 */
@Transactional
class UsermapperTest {
    @Autowired
    Usermapper usermapper;

    @Test
    public void getUserById(Integer id) {
        Userdao userdao = usermapper.getListById(id);

    }
}
