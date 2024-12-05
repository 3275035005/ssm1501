package com.garbage.service;

import com.garbage.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<Order>{

    List<Order> getMessageByGoodsId(Long id);


}
