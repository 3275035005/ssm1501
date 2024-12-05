package com.garbage.mapper;

import com.garbage.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * GoodsMapper继承基类
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
