package com.garbage.controller;

import com.garbage.entity.Knowledge;
import com.garbage.entity.KnowledgeWithBLOBs;
import com.garbage.service.KnowledgeService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对管理平台提供 垃圾分类知识信息接口
 */
@RequestMapping("knowledge")
@RestController
public class KnowledgeController {

    @Autowired
    private KnowledgeService service;

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
                       @RequestBody KnowledgeWithBLOBs data){
        PageResult<KnowledgeWithBLOBs> pageResult = service.pageQuery(data, current, size);
        return R.ok().put("list", pageResult);
    }


    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody KnowledgeWithBLOBs data){
        service.save(data);
        return R.ok();
    }

    /**
     * 修改数据
     * @param data
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody KnowledgeWithBLOBs data){
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
