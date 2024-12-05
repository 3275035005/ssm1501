package com.garbage.mapper;

import com.garbage.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserMapper继承基类
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getUserByUsername(@Param("username") String username);

    User getById(Long id);

    List<User> getRanking();


}
