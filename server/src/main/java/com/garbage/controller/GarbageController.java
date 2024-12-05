package com.garbage.controller;

import com.garbage.entity.Garbage;
import com.garbage.entity.User;
import com.garbage.service.GarbageService;
import com.garbage.service.UserService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对管理平台提供 垃圾分类信息接口
 */
@RequestMapping("garbage")
@RestController
public class GarbageController {

    @Autowired
    private GarbageService service;

    @Autowired
    private UserService userService;


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
                       @RequestBody Garbage data){
        PageResult<Garbage> pageResult = service.pageQuery(data, current, size);
        return R.ok().put("list", pageResult);
    }


    /**
     * 审核垃圾分类信息
     * @param data
     * @return
     */
    @PostMapping("audit")
    public R audit(@RequestBody Garbage data){
        data.setStatus("1");
        User user = userService.getById(data.getUserId());
        user.setPoint(user.getPoint() + data.getPoint());
        userService.update(user);
        service.audit(data);
        return R.ok();
    }


    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody Garbage data){

        service.save(data);
        return R.ok();
    }

    /**
     * 修改数据
     * @param data
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody Garbage data){

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
