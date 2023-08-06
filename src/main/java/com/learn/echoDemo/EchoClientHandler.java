package com.learn.echoDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 被通知 netty 活跃时发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("netty rock",
                StandardCharsets.UTF_8));
    }

    // 每当接受数据，都会调用这个方法
    // 由服务器发送的消息可能会被分块接收，例如 server 发送了 5 个字节，不能保证一次性被接受，这个方法可能被调用 2 次
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("client received: " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
