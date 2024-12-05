package com.garbage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.garbage.entity.User;
import com.garbage.mapper.UserMapper;
import com.garbage.service.UserService;
import com.garbage.utils.PageResult;
import com.garbage.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper usersMapper;


    @Override
    public PageResult<User> pageQuery(User data,int current, int size) {
        PageHelper.startPage(current, size);
        List<User> queryList = usersMapper.pageQuery(data);
        PageInfo<User> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public int save(User data) {
        data.setCreateTime(Util.getTime());
        return usersMapper.insert(data);
    }

    @Override
    public int update(User data) {
        return usersMapper.update(data);
    }

    @Override
    public List<User> getAll() {
        return usersMapper.getAll();
    }

    @Override
    public void delete(Long id) {
        usersMapper.delete(id);
    }


    @Override
    public User getUserByUsername(String username) {
        return usersMapper.getUserByUsername(username);
    }

    @Override
    public List<User> getRanking() {

        return usersMapper.getRanking();
    }

    @Override
    public User getById(Long id) {
        return usersMapper.getById(id);
    }
}
