package com.inter.rpc.provider.service.impl;

import com.inter.rpc.api.UserService;
import com.inter.rpc.pojo.User;
import com.inter.rpc.provider.anno.RpcService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shg
 * @create: 2022-03-03 9:58 下午
 */
@RpcService // 代表UserServiceImpl已经是对外暴露的了
@Service // 将UserServiceImpl加入到Spring IOC容器中
public class UserServiceImpl implements UserService {
    static Map<Integer, User> map = new HashMap<>();

    static {
        User user1 = new User();
        user1.setId(1);
        user1.setName("张三");

        User user2 = new User();
        user2.setId(2);
        user2.setName("李四");
        map.put(1, user1);
        map.put(2, user2);
    }

    @Override
    public User getById(int id) {
        return map.get(id);
    }
}
