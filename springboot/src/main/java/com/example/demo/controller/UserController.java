package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Account;
import com.example.demo.entity.Passenger;
import com.example.demo.mapper.PassengerMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;

    @Resource
    PassengerMapper passengerMapper;

    //注册账号
    @PostMapping("/register")
    public Result<?> register(@RequestBody Account account){
        Account account1 = userMapper.selectOne(Wrappers.<Account>lambdaQuery()
                .eq(Account::getNumber, account.getNumber()));
        if(account1 != null){
            return Result.error("-1","电话号码已存在！");
        }
        userMapper.insert(account);
        return Result.success();
    }

    //登陆
    @PostMapping("/login")
    public Result<?> login(@RequestBody Account account){
        Account account1 = userMapper.selectOne(Wrappers.<Account>lambdaQuery()
                .eq(Account::getNumber, account.getNumber())
                .eq(Account::getPassword, account.getPassword()));
        if(account1 == null){
            return Result.error("-2","用户名或密码错误!");
        }
        return Result.success();
    }

    //实名信息
    @PostMapping("/realName")
    public Result<?> realName(@RequestBody Passenger passenger){
        Passenger passenger1 = passengerMapper.selectOne(Wrappers.<Passenger>lambdaQuery().
                eq(Passenger::getIdCard, passenger.getIdCard()));
        if(passenger1 != null){
            return Result.error("-3","用户实名信息重复!");
        }
        passengerMapper.insert(passenger);
        return Result.success();
    }

    //用于更新实名信息
    @PostMapping("/update")
    public Result<?> update(@RequestBody Passenger passenger){
        LambdaUpdateWrapper<Passenger> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Passenger::getNumber, passenger.getNumber())
                .set(Passenger::getName,passenger.getName())
                .set(Passenger::getAge,passenger.getAge())
                .set(Passenger::getSex,passenger.getSex());
        int update = passengerMapper.update(null, lambdaUpdateWrapper);
        System.out.println(update);
        return Result.success();
    }

//    //查询过程
//    @GetMapping("/insert")
//    public Result<?> query(@RequestParam String id){
//        System.out.println(id);
//        try {
//            Map<String,String> params1 = new HashMap<>();
//            params1.put("id","1");
//            userMapper.alter_password("11111");
//        }catch (Exception e){
//            System.out.println(e);
//            return Result.error("-6","调用失败");
//        }
//        return Result.success();
//    }

    @GetMapping
    public Result<?> getRealName(@RequestParam String num){
        LambdaQueryWrapper<Passenger> wrapper = Wrappers.<Passenger>lambdaQuery().eq(Passenger::getNumber, num);
        Passenger passenger = passengerMapper.selectOne(wrapper);
        return Result.success(passenger);
    }

    //查询过程
    @GetMapping("/alterPassword")
    public Result<?> update(@RequestParam String number,@RequestParam String password){
        System.out.println(password);
        try {
            Map<String,String> params1 = new HashMap<>();
            params1.put("id","1");
            userMapper.alter_password(number,password);
        }catch (Exception e){
            System.out.println(e);
            return Result.error("-6","调用失败");
        }
        return Result.success();
    }
}
