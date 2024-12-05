package com.garbage.controller;

import com.garbage.entity.Goods;
import com.garbage.service.GoodsService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对管理平台提供 礼品管理接口
 */
@RequestMapping("goods")
@RestController
public class GoodsController {

    @Autowired
    private GoodsService service;

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
                       @RequestBody Goods data){
        PageResult<Goods> pageResult = service.pageQuery(data, current, size);
        return R.ok().put("list", pageResult);
    }


    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody Goods data){
        data.setSales(0);
        data.setPraise(0);
        service.save(data);
        return R.ok();
    }

    /**
     * 修改数据
     * @param data
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody Goods data){
        service.update(data);
        return R.ok();
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
