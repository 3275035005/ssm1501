package com.garbage.mapper;

import com.garbage.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderMapper继承基类
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> getMessageByGoodsId(@Param("id") Long id);

}
