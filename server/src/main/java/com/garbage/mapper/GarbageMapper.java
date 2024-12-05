package com.garbage.mapper;

import com.garbage.entity.Garbage;
import org.apache.ibatis.annotations.Mapper;

/**
 * GarbageMapper继承基类
 */
@Mapper
public interface GarbageMapper extends BaseMapper<Garbage> {
}
