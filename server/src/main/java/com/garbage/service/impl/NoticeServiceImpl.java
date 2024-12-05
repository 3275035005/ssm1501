package com.garbage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garbage.entity.Notice;
import com.garbage.mapper.NoticeMapper;
import com.garbage.service.NoticeService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeMapper NoticeMapper;


    @Override
    public PageResult<Notice> pageQuery(Notice data,int current, int size) {
        PageHelper.startPage(current, size);
        List<Notice> queryList = NoticeMapper.pageQuery(data);
        PageInfo<Notice> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(Notice data) {
        data.setCreateTime(Util.getTime());
        return NoticeMapper.insert(data);
    }

    @Override
    public int update(Notice data) {
        return NoticeMapper.update(data);
    }

    @Override
    public List<Notice> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        NoticeMapper.delete(id);
    }

    @Override
    public Notice getById(Long id) {
        return NoticeMapper.getById(id);
    }

    @Override
    public List<Notice> getNoticeLimit8() {
        return NoticeMapper.getNoticeLimit8();
    }
}
