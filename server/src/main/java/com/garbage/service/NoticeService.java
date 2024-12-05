package com.garbage.service;

import com.garbage.entity.Notice;

import java.util.List;

public interface NoticeService extends BaseService<Notice>{

    List<Notice> getNoticeLimit8();
}
