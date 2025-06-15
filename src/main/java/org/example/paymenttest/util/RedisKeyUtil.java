package org.example.paymenttest.util;

public class RedisKeyUtil {

    public static String getUnreadUserSetKey(String roomId, String messageId) {
        return String.format("chat:unread:%s:%s",roomId,messageId);
    }

    public static String getLastReadMessageKey(String roomId, String username) {
        return String.format("chat:lastread:%s:%s",roomId,username);
    }

}
