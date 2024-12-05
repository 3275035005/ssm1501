package com.garbage.service.impl;

import com.garbage.entity.Knowledge;
import com.garbage.entity.KnowledgeWithBLOBs;
import com.garbage.mapper.KnowledgeMapper;
import com.garbage.service.KnowledgeService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService{

    @Autowired
    private KnowledgeMapper KnowledgeMapper;


    @Override
    public PageResult<KnowledgeWithBLOBs> pageQuery(KnowledgeWithBLOBs data,int current, int size) {
        PageHelper.startPage(current, size);
        List<KnowledgeWithBLOBs> queryList = KnowledgeMapper.pageQuery(data);
        PageInfo<KnowledgeWithBLOBs> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(KnowledgeWithBLOBs data) {
        data.setCreateTime(Util.getTime());
        return KnowledgeMapper.insert(data);
    }

    @Override
    public int update(KnowledgeWithBLOBs data) {
        return KnowledgeMapper.update(data);
    }

    @Override
    public List<KnowledgeWithBLOBs> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        KnowledgeMapper.delete(id);
    }

    @Override
    public KnowledgeWithBLOBs getById(Long id) {
        return KnowledgeMapper.getById(id);
    }

}
