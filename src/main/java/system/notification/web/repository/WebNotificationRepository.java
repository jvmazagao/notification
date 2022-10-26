package system.notification.web.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import system.notification.web.repository.model.WebNotificationModel;

@Component
public interface WebNotificationRepository extends MongoRepository<WebNotificationModel, String> {
}
