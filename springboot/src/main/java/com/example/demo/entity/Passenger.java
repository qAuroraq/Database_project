package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("passenger")
@Data
public class Passenger {
    private String name;
    private String idCard;
    private Integer age;
    private String sex;
    private String number;
}
