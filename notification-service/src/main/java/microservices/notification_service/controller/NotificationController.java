package microservices.notification_service.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import microservices.event.dto.NotificationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.management.Notification;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    @KafkaListener(topics = "notification-delivery")
    public void listen(NotificationEvent message) {
        log.info("Message received: {}", message);
        log.info("channel: {}", message.getChannel());
    }
}
