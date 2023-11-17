package instaface.backend.config;

import instaface.backend.domain.entities.User;
import instaface.backend.services.UserService;
import instaface.backend.utils.responseHandler.exceptions.CustomException;
import instaface.backend.validations.serviceValidation.services.UserValidationService;
import io.jsonwebtoken.Jwts;
import instaface.backend.web.websocket.JWTAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Optional;

import static instaface.backend.utils.constants.ResponseMessageConstants.UNAUTHORIZED_SERVER_ERROR_MESSAGE;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class ApplicationWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    private final UserService userService;
    private final UserValidationService userValidation;

    @Autowired
    public ApplicationWebSocketConfiguration(UserService userService, UserValidationService userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    /**
     * Register Stomp endpoints: the url to open the WebSocket connection.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đăng ký điểm cuối "/socket", kích hoạt giao thức SockJS.
        // SockJS được sử dụng (cả phía máy khách và phía máy chủ) để cho phép thay thế
        // tùy chọn nhắn tin nếu WebSocket không có sẵn.
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
// // Kích hoạt trình môi giới tin nhắn dựa trên bộ nhớ đơn giản để gửi tin nhắn đến
// // client trên các đích có tiền tố "/app".
// // Nhà môi giới tin nhắn đơn giản xử lý các yêu cầu đăng ký từ khách hàng, cửa hàng
// // chúng trong bộ nhớ và phát các tin nhắn đến các máy khách được kết nối với
// // các điểm đến phù hợp.
        registry.setApplicationDestinationPrefixes("/app")
                .setUserDestinationPrefix("/user")
                .enableSimpleBroker("/chat", "/topic", "/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Optional.ofNullable(accessor.getNativeHeader("Authorization")).ifPresent(ah -> {
                        String bearerToken = ah.get(0).replace("Bearer ", "");
                        JWTAuthenticationToken token = getJWTAuthenticationToken(bearerToken);
                        accessor.setUser(token);
                    });
                }
                return message;
            }
        });
    }

    private JWTAuthenticationToken getJWTAuthenticationToken(String token) {
        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey("Secret".getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            if (username != null) {
                UserDetails userData = this.userService
                        .loadUserByUsername(username);

                if (!userValidation.isValid(userData)) {
                    throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
                }

                JWTAuthenticationToken jwtAuthenticationToken =
                        new JWTAuthenticationToken(userData.getAuthorities(), token, (User) userData);

                jwtAuthenticationToken.setAuthenticated(true);

                return jwtAuthenticationToken;
            }
        }

        return null;
    }
}
