package com.garbage.service.impl;

import com.garbage.entity.Garbage;
import com.garbage.mapper.GarbageMapper;
import com.garbage.service.GarbageService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarbageServiceImpl implements GarbageService{

    @Autowired
    private GarbageMapper GarbageMapper;


    @Override
    public PageResult<Garbage> pageQuery(Garbage data,int current, int size) {
        PageHelper.startPage(current, size);
        List<Garbage> queryList = GarbageMapper.pageQuery(data);
        PageInfo<Garbage> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(Garbage data) {
        data.setCreateTime(Util.getTime());
        return GarbageMapper.insert(data);
    }

    @Override
    public int update(Garbage data) {
        return GarbageMapper.update(data);
    }

    @Override
    public List<Garbage> getAll() {
        return GarbageMapper.getAll();
    }

    @Override
    public void delete(Long id) {
        GarbageMapper.delete(id);
    }

    @Override
    public Garbage getById(Long id) {
        return GarbageMapper.getById(id);
    }

    @Override
    public void audit(Garbage data) {
       GarbageMapper.update(data);
    }
}
