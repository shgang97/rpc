package com.inter.rpc.consumer.client;

/**
 * @author: shg
 * @create: 2022-03-04 12:30 上午
 */

import com.inter.rpc.consumer.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 客户端
 * 1. 连接Netty服务端
 * 2. 提供给调用者主动关闭资源的方法
 * 3. 提供消息发送的方法
 */
public class RpcClient {
    private String ip;
    private int port;
    private EventLoopGroup group;
    private Channel channel;
    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService pool = Executors.newCachedThreadPool();

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    /**
     * 初始化方法-连接Netty服务端
     */
    public void initClient() {
        try {
            // 1. 创建线程组
            group = new NioEventLoopGroup();
            // 2. 创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3. 设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)// 设置通道活跃的状态
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)// 设置连接超时的时间
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // String编解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 添加客户端处理类
                            pipeline.addLast(rpcClientHandler);

                        }
                    });
            // 4. 连接Netty服务端
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (group != null) group.shutdownGracefully();
            if (channel != null) channel.close();
        }
    }

    /**
     * 2. 提供给调用者主动关闭资源的方法
     */
    public void close() {
        if (group != null) group.shutdownGracefully();
        if (channel != null) channel.close();
    }

    /**
     * 3. 提供消息发送的方法
     * @return
     */
    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMessage(msg);
        Future future = pool.submit(rpcClientHandler);
        return future.get();
    }
}
