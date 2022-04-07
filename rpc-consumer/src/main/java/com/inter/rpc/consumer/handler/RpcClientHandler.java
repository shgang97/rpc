package com.inter.rpc.consumer.handler;

/**
 * @author: shg
 * @create: 2022-03-04 12:43 上午
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * 客户端处理类
 * 1. 发送消息
 * 2. 接收消息
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    ChannelHandlerContext ctx;
    // 发送的消息
    String requestMessage;
    // 服务端返回的消息
    String responseMessage;

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    /**
     * 通道连接就绪事件
     * 当该方法执行时，说明通道和服务端连接好了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    /**
     * 通道读取就绪事件
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        responseMessage = msg;
        notify();
    }

    /**
     * 放消息到服务端
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        // 消息发送
        ctx.writeAndFlush(requestMessage);
        // 线程等待
        wait();
        return responseMessage;
    }
}
