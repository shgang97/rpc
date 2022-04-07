package com.inter.rpc.common;

/**
 * @author: shg
 * @create: 2022-03-03 9:42 下午
 */

import lombok.Data;

/**
 * 封装的请求对象
 */
@Data
public class RpcRequest {
    /**
     * 请求对象的Id
     */
    private String requestId;
    /**
     * 累名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
