package com.garbage.mapper;

import com.garbage.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NoticeMapper继承基类
 */
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> getNoticeLimit8();
}
