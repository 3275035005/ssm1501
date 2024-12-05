package com.garbage.service.impl;

import com.garbage.entity.Order;
import com.garbage.mapper.OrderMapper;
import com.garbage.service.OrderService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper OrderMapper;


    @Override
    public PageResult<Order> pageQuery(Order data,int current, int size) {
        PageHelper.startPage(current, size);
        List<Order> queryList = OrderMapper.pageQuery(data);
        PageInfo<Order> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int save(Order data) {
        data.setCreateTime(Util.getTime());
        int insert = OrderMapper.insert(data);

        System.out.println(insert);
        return insert;
    }

    @Override
    public int update(Order data) {
        return OrderMapper.update(data);
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public void delete(Long id) {
        OrderMapper.delete(id);
    }

    @Override
    public Order getById(Long id) {
        return OrderMapper.getById(id);
    }

    @Override
    public List<Order> getMessageByGoodsId(Long id) {

       return OrderMapper.getMessageByGoodsId(id);
    }
}
