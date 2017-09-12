
package com.huan.HTed.message.impl;

import com.huan.HTed.message.IMessageConsumer;

/**
 * 用于测试.
 * <p>
 * 直接打印收到的消息
 */
public class SimpleMessageConsumer implements IMessageConsumer<String> {
    @Override
    public void onMessage(String message, String pattern) {

    }
}
