
package com.huan.HTed.message;

public interface IMessagePublisher {

    /**
     * publish message to a channel.
     * 
     * @param channel
     *            channel
     * @param message
     *            message :String ,Number, Map, Object...
     */
    void publish(String channel, Object message);

    /**
     * add message to a queue .
     * 
     * @param list
     *            destination
     * @param message
     *            message :String ,Number, Map, Object...
     */
    void rPush(String list, Object message);
}
