package com.garbage.controller;

import com.garbage.entity.User;
import com.garbage.service.UserService;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对管理平台提供 用户管理接口
 */
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("login")
    public R login(@RequestBody User users){
        User userss = userService.getUserByUsername(users.getUsername());
        if(userss == null){
            return R.error("请输入正确的账号");
        }
        if(!userss.getPassword().equals(users.getPassword())){
            return R.error("请输入正确的密码");
        }
        String status = userss.getStatus();
        if("0".equals(status)){
            return R.error("账号已被禁用");
        }
        return R.ok().put("row", userss);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public R register(@RequestBody User users){
        users.setRole("1");
        users.setStatus("1");
        userService.save(users);
        return R.ok();
    }

    /**
     * 通过用户表id查询数据
     */
    @GetMapping("getUserById")
    public R getUserById(Long id){
        User userss = userService.getById(id);
        return R.ok().put("row", userss);
    }

    /**
     * 查询所有用户数据
     */
    @GetMapping("getAll")
    public R getAll(){
        List<User> all = userService.getAll();

        return R.ok().put("row", all);
    }


    /**
     * 分页添加查询数据
     * @param current 当前页码
     * @param size 每页大小
     * @param user 查询数据
     * @return
     */
    @PostMapping("pageQuery/{current}/{size}")
    public R pageQuery(@PathVariable int current,
                       @PathVariable int size,
                       @RequestBody User user){
        PageResult<User> pageResult = userService.pageQuery(user, current, size);
        return R.ok().put("list", pageResult);
    }


    /**
     * 新增数据
     * @param user
     * @return
     */
    @PostMapping("save")
    public R save(@RequestBody User user){
        user.setRole("1");
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改数据
     * @param user
     * @return
     */
    @PostMapping("update")
    public R update(@RequestBody User user){
        userService.update(user);
        return R.ok();
    }


    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable Long id){
        userService.delete(id);
        return R.ok();
    }
}
