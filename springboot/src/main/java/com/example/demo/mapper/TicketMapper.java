package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Ticket;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface TicketMapper extends BaseMapper<Ticket> {

    @Select("CALL query(#{1, mode=IN, jdbcType=VARCHAR})")
    @Options(statementType = StatementType.CALLABLE)
    void query();

//    @Update("CALL alter_password(#{1111, mode=IN, jdbcType=VARCHAR})")
//    @Options(statementType = StatementType.CALLABLE)
//    void alter_password();
}
