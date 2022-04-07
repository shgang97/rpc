package com.inter.rpc.common;

/**
 * @author: shg
 * @create: 2022-03-03 9:44 下午
 */

import lombok.Data;

/**
 * 封装的响应对象
 */
@Data
public class RpcResponse {
    /**
     * 响应的Id
     */
    private String requestId;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 返回的结果
     */
    private Object result;

}
