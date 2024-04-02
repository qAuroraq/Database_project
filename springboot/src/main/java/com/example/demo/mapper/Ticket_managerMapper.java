package com.example.demo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Ticket_manager;
import org.apache.ibatis.annotations.Delete;

public interface Ticket_managerMapper extends BaseMapper<Ticket_manager>{
//    @Insert("CALL insert_train(#{6, mode=IN, jdbcType=VARCHAR},#{3, mode=IN, jdbcType=VARCHAR},#{北京, mode=IN, jdbcType=VARCHAR},#{2023-05-23 09:44:26, mode=IN, jdbcType=TIMESTAMP},#{昆明, mode=IN, jdbcType=VARCHAR},#{2023-05-25 07:00:00, mode=IN, jdbcType=TIMESTAMP},#{商务座, mode=IN, jdbcType=ENUM},#{高铁, mode=IN, jdbcType=ENUM},#{30, mode=IN, jdbcType=SMALLINT},#{600, mode=IN, jdbcType=SMALLINT})")
//    void insert_ticket();
    @Delete("delete from ticket_manager where checi=#{checi} and train_number=#{trainNumber}")
    int delete(String checi, String trainNumber);
}
