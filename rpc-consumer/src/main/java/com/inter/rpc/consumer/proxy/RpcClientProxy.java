package com.inter.rpc.consumer.proxy;

/**
 * @author: shg
 * @create: 2022-03-04 12:55 上午
 */

import com.alibaba.fastjson.JSON;
import com.inter.rpc.common.RpcRequest;
import com.inter.rpc.common.RpcResponse;
import com.inter.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 客户端代理类-创建代理对象
 * 1. 封装request请求对象
 * 2. 创建RpcClient对象
 * 3. 发送消息
 * 4. 返回结果
 */
public class RpcClientProxy {
    // 客户端代理类-使用JDK动态代理创建代理对象，
    public static Object createProxy(Class serviceClass) {
        // 获取一个类加载器——当前线程的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return Proxy.newProxyInstance(
                classLoader,
                new Class[]{serviceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 1. 封装request请求对象
                        RpcRequest rpcRequest = new RpcRequest();
                        rpcRequest.setRequestId(UUID.randomUUID().toString());
                        rpcRequest.setClassName(method.getDeclaringClass().getName());
                        rpcRequest.setParameterTypes(method.getParameterTypes());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setParameters(args);
                        // 2. 创建RpcClient对象
                        RpcClient rpcClient = new RpcClient("127.0.0.1", 9797);
                        try {
                            // 3. 发送消息
                            Object responseMessage = rpcClient.send(JSON.toJSONString(rpcRequest));
                            RpcResponse rpcResponse = JSON.parseObject(responseMessage.toString(), RpcResponse.class);
                            if (rpcResponse.getError() != null) {
                                throw new RuntimeException(rpcResponse.getError());
                            }
                            // 4. 返回结果
                            Object result = rpcResponse.getResult();
                            return JSON.parseObject(result.toString(), method.getReturnType());
                        } catch (Exception e) {
                            throw e;
                        } finally {
                            rpcClient.close();
                        }
                    }
                }
        );
    }
}
