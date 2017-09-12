
package com.huan.HTed.message;

public interface IMessageConsumer<T> {
    String DEFAULT_METHOD_NAME = "onMessage";

    /**
     * 
     * @param message
     *            经过反序列化的消息
     * @param pattern
     *            list name or channel
     */
    void onMessage(T message, String pattern);
}
