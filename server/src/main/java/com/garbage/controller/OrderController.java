package com.garbage.controller;

import com.garbage.entity.Order;
import com.garbage.service.OrderService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对管理平台提供 订单管理接口
 */
@RequestMapping("order")
@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    /**
     * 分页添加查询数据
     * @param current 当前页码
     * @param size 每页大小
     * @param data 查询数据
     * @return
     */
    @PostMapping("pageQuery/{current}/{size}")
    public R pageQuery(@PathVariable int current,
                       @PathVariable int size,
                       @RequestBody Order data){
        PageResult<Order> pageResult = service.pageQuery(data, current, size);
        return R.ok().put("list", pageResult);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable Long id){
        service.delete(id);
        return R.ok();
    }
}
