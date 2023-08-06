package com.learn.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBufTest {
    /**
     * 将数据存储在 JVM 的堆空间中，可以在没有使用池化的情况下提供快速分配和释放
     */
    private static void heapBufTest() {
        ByteBuf heapBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi\r\n", StandardCharsets.UTF_8));
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            // 计算第一个字节的偏移量
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            // 可读字节数
            int length = heapBuf.readableBytes();
            handleArray(array, offset, length);
        }
    }

    /**
     * 通过本地调用分配内存，避免了每次调用本地 IO 前后将缓冲区的内容复制到一个中间缓冲区，减少成本
     */
    private static void directBufTest() {
        ByteBuf directBuf = Unpooled.directBuffer().writeBytes("hi".getBytes(StandardCharsets.UTF_8));
        if (!directBuf.hasArray()) {  // 检查 ByteBuf 是否由数组支撑，如果不是这是一个直接缓冲区
            int length = directBuf.readableBytes();
            byte[] bytes = new byte[length];
            // 将字节复制到 bytes 数组
            directBuf.getBytes(directBuf.readerIndex(), bytes);
            handleArray(bytes, 0, length);
        }
    }

    /**
     * 支持多个缓冲区表示成为单个合并的缓冲区
     */
    private static void compositeBuf() {
        CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer();
        ByteBuf head = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hello ", StandardCharsets.UTF_8));
        ByteBuf body = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("world\n", StandardCharsets.UTF_8));
        compositeBuffer.addComponents(head, body);
        compositeBuffer.removeComponent(0);  // remove head
        for (ByteBuf byteBuf : compositeBuffer) {
            System.out.println(byteBuf.toString(StandardCharsets.UTF_8));
        }
    }

    private static void handleArray(byte[] arr, int offset, int length) {
        byte[] bytes = new byte[length];
        int cur = 0;
        for (int i = offset; i < offset + length; i++) {
            bytes[cur ++] = arr[i];
        }
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
//        heapBufTest();
//        directBufTest();
//        compositeBuf();

    }
}
