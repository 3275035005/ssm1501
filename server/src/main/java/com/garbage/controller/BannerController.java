package com.garbage.controller;

import com.garbage.entity.Banner;
import com.garbage.service.BannerService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对管理平台提供 轮播图接口
 */
@RequestMapping("banner")
@RestController
public class BannerController {

    @Autowired
    private BannerService service;

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
                       @RequestBody Banner data){
        PageResult<Banner> pageResult = service.pageQuery(data, current, size);
        return R.ok().put("list", pageResult);
    }


    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody Banner data){
        service.save(data);
        return R.ok();
    }

    /**
     * 修改数据
     * @param data
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody Banner data){
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
