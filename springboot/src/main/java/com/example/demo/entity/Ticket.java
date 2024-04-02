package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("ticket")
@Data
public class Ticket {
    private Integer ticketId;
    private String trainNumber;
    private String checi;
    private String src;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date srcTime;
    private String dst;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date dstTime;

    private String trainType;
    private String seatType;
    private Integer carriageNumber;
    private String seatNum;
    private String ticketType;
    private String idCard;

}
