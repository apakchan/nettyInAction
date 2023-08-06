package com.learn.channelHandlerPipeline;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 可共享的 ChannelHandler
 * 可以安全用于多个并发的 channel
 * 必须线程安全
 */
@ChannelHandler.Sharable
public class SharableHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channel read msg: " + msg);
        // 记录方法调用，转发给下一个 ChannelHandler
        ctx.fireChannelRead(msg);
    }
}
