package com.garbage.mapper;

import com.garbage.entity.Knowledge;
import com.garbage.entity.KnowledgeWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

/**
 * KnowledgeMapper继承基类
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<KnowledgeWithBLOBs> {
}
