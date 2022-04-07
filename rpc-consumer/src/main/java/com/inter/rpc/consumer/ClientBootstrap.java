package com.inter.rpc.consumer;

/**
 * @author: shg
 * @create: 2022-03-04 1:15 上午
 */

import com.inter.rpc.api.UserService;
import com.inter.rpc.consumer.proxy.RpcClientProxy;
import com.inter.rpc.pojo.User;

/**
 * 测试类
 */
public class ClientBootstrap {
    public static void main(String[] args) {
        UserService userService = (UserService) RpcClientProxy.createProxy(UserService.class);
        User user = userService.getById(2);
        System.out.println(user);
    }
}
