package com.inter.rpc.provider;

import com.inter.rpc.provider.handler.RpcServerHandler;
import com.inter.rpc.provider.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: shg
 * @create: 2022-03-03 10:09 下午
 */
@SpringBootApplication
public class ServerBootstrapApplication implements CommandLineRunner {

    @Autowired
    RpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication.run(ServerBootstrapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            rpcServer.startServer("127.0.0.1",9797);
        }).start();
    }
}
