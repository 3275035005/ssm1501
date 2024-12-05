package com.garbage.service.impl;

import com.garbage.entity.Goods;
import com.garbage.mapper.GoodsMapper;
import com.garbage.service.GoodsService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsMapper GoodsMapper;


    @Override
    public PageResult<Goods> pageQuery(Goods data,int current, int size) {
        PageHelper.startPage(current, size);
        List<Goods> queryList = GoodsMapper.pageQuery(data);
        PageInfo<Goods> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(Goods data) {
        data.setCreateTime(Util.getTime());
        return GoodsMapper.insert(data);
    }

    @Override
    public int update(Goods data) {
        return GoodsMapper.update(data);
    }

    @Override
    public List<Goods> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        GoodsMapper.delete(id);
    }

    @Override
    public Goods getById(Long id) {
        return GoodsMapper.getById(id);
    }

}
