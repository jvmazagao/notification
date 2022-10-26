package system.notification.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import system.notification.core.entity.NotificationStatus;
import system.notification.core.publisher.Publisher;
import system.notification.core.publisher.sns.SNSPublisher;
import system.notification.core.service.NotificationService;
import system.notification.customer.adapter.DisableNotification;
import system.notification.web.entity.WebNotification;
import system.notification.web.repository.WebNotificationRepository;
import system.notification.web.repository.model.WebNotificationModel;

@Service
public class WebNotificationService implements NotificationService<WebNotification> {

  private final MongoRepository<WebNotificationModel, String> repository;
  private final Publisher<String> publisher;
  private final DisableNotification disableNotification;
  @Autowired
  public WebNotificationService(
      WebNotificationRepository repository,
      SNSPublisher publisher,
      DisableNotification disableNotification
  ) {
    this.repository = repository;
    this.publisher = publisher;
    this.disableNotification = disableNotification;
  }

  @Override
  public WebNotification store(WebNotification entity) {
    if (!this.disableNotification.shouldNotify(entity.getCustomer())) {
      entity.setStatus(NotificationStatus.DISABLED);
      return entity;
    }

    this.repository.save(WebNotificationModel.fromEntity(entity));
    entity.setStatus(NotificationStatus.SENT);
    this.publisher.publish(entity.getTitle());
    return entity;
  }

  @Override
  public WebNotification schedule(WebNotification entity) {
    if (!this.disableNotification.shouldNotify(entity.getCustomer())) {
      entity.setStatus(NotificationStatus.DISABLED);
      return entity;
    }

    this.repository.save(WebNotificationModel.fromEntity(entity));
    entity.setStatus(NotificationStatus.SCHEDULED);
    return entity;
  }
}
