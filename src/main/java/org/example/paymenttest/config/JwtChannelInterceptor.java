package org.example.paymenttest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

//        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("token");

            if(token != null && !token.isEmpty()) {
                try {
                    Authentication auth = jwtUtil.getAuthentication(token);
                    accessor.setUser(auth);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid token");
                }
            }
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());

    }

}
