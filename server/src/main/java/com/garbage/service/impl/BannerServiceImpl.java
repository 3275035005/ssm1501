package com.garbage.service.impl;

import com.garbage.entity.Banner;
import com.garbage.mapper.BannerMapper;
import com.garbage.service.BannerService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService{

    @Autowired
    private BannerMapper BannerMapper;


    @Override
    public PageResult<Banner> pageQuery(Banner data,int current, int size) {
        PageHelper.startPage(current, size);
        List<Banner> queryList = BannerMapper.pageQuery(data);
        PageInfo<Banner> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(Banner data) {
        data.setCreateTime(Util.getTime());
        return BannerMapper.insert(data);
    }

    @Override
    public int update(Banner data) {
        return BannerMapper.update(data);
    }

    @Override
    public List<Banner> getAll() {
        return BannerMapper.getAll();
    }

    @Override
    public void delete(Long id) {
        BannerMapper.delete(id);
    }

    @Override
    public Banner getById(Long id) {
        return null;
    }

}
