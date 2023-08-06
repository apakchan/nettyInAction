package com.learn.eventLoop;

import com.learn.echoDemo.EchoClientHandler;
import com.learn.echoDemo.EchoServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class EventLoopTest {
    public static void main(String[] args) {
        Channel ch = new NioSocketChannel();
        // 调度任务从现在开始的 60 后执行
        // 60s 后 Runnable 实例将由分配给 Channel 的 EventLoop 执行
        ScheduledFuture<?> future = ch.eventLoop().schedule(() -> {
            System.out.println("60s later");
        }, 60, TimeUnit.SECONDS);
        // 调度在 60s 后，每隔 60s 运行一次
        ch.eventLoop().scheduleAtFixedRate(() -> {
            System.out.println("60s later");
        }, 60, 60, TimeUnit.SECONDS);
    }
}
