package com.garbage.service;

import com.garbage.entity.User;

import java.util.List;

public interface UserService extends BaseService<User>{

    /**
     * 通过账号查询数据
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 排行榜
     * @return
     */
    List<User> getRanking();

}
