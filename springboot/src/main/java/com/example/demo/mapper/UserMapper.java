package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.Map;
@Mapper
public interface UserMapper extends BaseMapper<Account> {
//    @Select("CALL alter_password(#{11, mode=IN, jdbcType=VARCHAR})")
//    @Options(statementType = StatementType.CALLABLE)
    void alter_password(String number,String password);
}
