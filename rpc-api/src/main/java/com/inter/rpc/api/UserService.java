package com.inter.rpc.api;

/**
 * @author: shg
 * @create: 2022-03-03 9:41 下午
 */

import com.inter.rpc.pojo.User;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    User getById(int id);
}
