package com.example.demo.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Account;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.Ticket_manager;
import com.example.demo.entity.Ticket_information;
import com.example.demo.mapper.TicketMapper;
import com.example.demo.mapper.Ticket_managerMapper;
import com.example.demo.mapper.Ticket_informationMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ticketManager")
public class Ticket_managerController {
    @Resource
    Ticket_managerMapper ticket_managerMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    Ticket_informationMapper ticket_informationMapper;

    @Resource
    TicketMapper ticketMapper;

    //用于获得个人买的票
    @GetMapping("/myTicket")
    public Result<?> getPersonalTicket(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "5") Integer pageSize,
                                       @RequestParam String id)
    {
        LambdaQueryWrapper<Ticket> eq = Wrappers.<Ticket>lambdaQuery().eq(Ticket::getIdCard, id);
        Page<Ticket> ticket = ticketMapper.selectPage(new Page<>(currentPage, pageSize), eq);
        return Result.success(ticket);
    }

    //用于根据(出发站，目的站，出发时间)查询符合要求的车票
    @PostMapping
    public Result<?> findPage(@RequestBody Map<String,Object> map1){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int pageNum = (int) map1.get("currentPage");
        int pageSize  = (int) map1.get("pageSize");

        Ticket_information ticket_information = new Ticket_information();
        ticket_information.setCheci((String) map1.get("checi"));
        ticket_information.setDst((String) map1.get("dst"));
        ticket_information.setTrainNumber((String) map1.get("trainNumber"));
        ticket_information.setSrc((String) map1.get("src"));
        System.out.println(ticket_information);
        try {
            ticket_information.setSrcTime(simpleDateFormat.parse((String)map1.get("srcTime")));
        } catch (Exception e) {
            System.out.println(e);
            return Result.error("-4","查询失败!");
        }
        //对象查询
        LambdaQueryWrapper<Ticket_information> wrapper = Wrappers.<Ticket_information>lambdaQuery()
                .apply("date_format(src_time, '%Y-%m-%d') = {0}",simpleDateFormat.format(ticket_information.getSrcTime()))
                .eq(Ticket_information::getSrc, ticket_information.getSrc())
                .eq(Ticket_information::getDst, ticket_information.getDst());
        //查询
        Page<Ticket_information> ticket_managerPage = ticket_informationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ticket_managerPage);
    }


    @PostMapping("/manageTrain")
    public Result<?> findPage1(@RequestBody Map<String,Object> map1){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int pageNum = (int) map1.get("currentPage");
        int pageSize  = (int) map1.get("pageSize");

        Ticket_manager ticket_manager = new Ticket_manager();
        ticket_manager.setCheci((String) map1.get("checi"));
        ticket_manager.setDst((String) map1.get("dst"));
        ticket_manager.setTrainNumber((String) map1.get("trainNumber"));
        ticket_manager.setSrc((String) map1.get("src"));
        System.out.println(ticket_manager);
        try {
            ticket_manager.setSrcTime(simpleDateFormat.parse((String)map1.get("srcTime")));
        } catch (Exception e) {
            System.out.println(e);
            return Result.error("-4","查询失败!");
        }
        //对象查询
        LambdaQueryWrapper<Ticket_manager> wrapper = Wrappers.<Ticket_manager>lambdaQuery()
                .apply("date_format(src_time, '%Y-%m-%d') = {0}",simpleDateFormat.format(ticket_manager.getSrcTime()))
                .eq(Ticket_manager::getSrc, ticket_manager.getSrc())
                .eq(Ticket_manager::getDst, ticket_manager.getDst());
        //查询
        Page<Ticket_manager> ticket_managerPage = ticket_managerMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ticket_managerPage);
    }
    //用于返回对应座位类型的车票数
    @PostMapping("/getRestBySeatType")
    public Result<?> getRestBySeatType(@RequestBody Ticket_manager ticket_manager) {
        LambdaQueryWrapper<Ticket_manager> wrapper = Wrappers.<Ticket_manager>lambdaQuery()
                .eq(Ticket_manager::getSrc,ticket_manager.getSrc())
                .eq(Ticket_manager::getDst,ticket_manager.getDst())
                .eq(Ticket_manager::getCheci,ticket_manager.getCheci())
                .eq(Ticket_manager::getTrainNumber,ticket_manager.getTrainNumber())
                .eq(Ticket_manager::getSeatType,ticket_manager.getSeatType());
        Ticket_manager ticket_manager1 = ticket_managerMapper.selectOne(wrapper);
        if (ticket_manager1==null){
            return Result.error("-5","无票！");
        }
        else{
            return Result.success(ticket_manager1);
        }
    }

    @GetMapping("/delete")
    public Result<?> delete(@RequestParam String Checi, @RequestParam String TrainNumber){
        System.out.println(Checi);
        System.out.println(TrainNumber);
        ticket_managerMapper.delete(Checi,TrainNumber);
        return Result.success();
    }

    @PostMapping("/insert")
    public Result<?> insert(@RequestBody Ticket_manager ticket_manager){
        System.out.println(ticket_manager.toString());
        ticket_managerMapper.insert(ticket_manager);
        return Result.success();
    }

    @GetMapping("/interceptor")
    public void interceptor(){

    }

    @GetMapping("/backup")
    public Result<?> backup(){
        System.out.println("!!!!");
        //预存想要执行的命令
        String command;
        //我要执行的命令
        command = "cmd.exe /c mysqldump -uroot -p123mysql 12306_test -B > D:/mysql/data/db5.sql";
        try{
            Process process = Runtime.getRuntime().exec(command); // 执行命令
            int exitCode = process.waitFor(); // 等待命令执行完成并获取退出码
            System.out.println("Command exited with code " + exitCode);
            // 销毁process对象
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @GetMapping("/restore")
    public Result<?> restore(){
        System.out.println("!");
        //预存想要执行的命令
        String command;
        //我要执行的命令
        command = "cmd.exe /c mysql -uroot -p123mysql < D:/mysql/data/db5.sql";
        try{
            Process process = Runtime.getRuntime().exec(command); // 执行命令
            int exitCode = process.waitFor(); // 等待命令执行完成并获取退出码
            System.out.println("Command exited with code " + exitCode);
            // 销毁process对象
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @PostMapping("/buy")
    public Result<?> buyTicket(@RequestBody Ticket ticket) {
        try{
            ticketMapper.insert(ticket);
        }
        catch(Exception e){
            System.out.println(e);
            return Result.error("-6","购买失败！");
        }
        return Result.success();
    }

    @PostMapping("/return")
    public Result<?> returnTicket(@RequestBody Ticket ticket){
        HashMap<String,Object> map = new HashMap<>();
        map.put("ticket_id",ticket.getTicketId());
        try{
            ticketMapper.deleteByMap(map);
        }
        catch (Exception e){
            return Result.error("-6","退票失败!");
        }
        return Result.success();
    }

    //存储过程
//    @PostMapping("/update")
//    public Result<?> insertTicket(@RequestBody Ticket_manager ticket_information){
//        try {
//            userMapper.insert_ticket(ticket_information);
//        }catch (Exception e){
//            System.out.println(e);
//            return Result.error("-6","调用失败");
//        }
//        return Result.success();
//    }
}
