package com.example.demo.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("ticket_information")
@Data
public class Ticket_information {
    //驼峰命名自动转下划线
    private String trainNumber;
    private String checi;
    private String src;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date srcTime;

    private String dst;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date dstTime;

    private String trainType;
}
