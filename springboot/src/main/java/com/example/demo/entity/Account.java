package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//连接数据库，用lombok简化getter和setter
@TableName("account")
@Data
public class Account {
    private String number;
    private String password;
}
