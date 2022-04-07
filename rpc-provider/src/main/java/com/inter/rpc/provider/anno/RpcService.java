package com.inter.rpc.provider.anno;

/**
 * @author: shg
 * @create: 2022-03-03 10:11 下午
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对外暴露服务接口
 * 加到UserServiceImpl上
 */
@Target(ElementType.TYPE) // 用于接口和类上
@Retention(RetentionPolicy.RUNTIME) // 在运行时可以获取到
public @interface RpcService {
}
