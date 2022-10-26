package system.notification.web.service;


import org.junit.jupiter.api.Test;
import system.notification.TestCase;
import system.notification.core.entity.Customer;
import system.notification.core.entity.NotificationStatus;
import system.notification.core.publisher.sns.SNSPublisher;
import system.notification.core.service.NotificationService;
import system.notification.customer.adapter.DisableNotification;
import system.notification.web.entity.Content;
import system.notification.web.entity.WebNotification;
import system.notification.web.repository.WebNotificationRepository;
import system.notification.web.repository.model.WebNotificationModel;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WebNotificationServiceTest extends TestCase {

  WebNotificationRepository repository = mock(WebNotificationRepository.class);
  SNSPublisher publisher = mock(SNSPublisher.class);
  DisableNotification disableNotification = mock(DisableNotification.class);

  @Test
  public void storeNotification() {
    WebNotification entity = createEntity(
        1,
        "title",
        new ArrayList<Content>(),
        "url"
    );

    NotificationService<WebNotification> service = new WebNotificationService(
        this.repository,
        this.publisher,
        this.disableNotification
    );
    when(this.disableNotification.shouldNotify(entity.getCustomer())).thenReturn(Boolean.TRUE);
    WebNotification notification = service.store(entity);
    WebNotification expected = createEntity(
        1,
        "title",
        new ArrayList<Content>(),
        "url"
    );
    expected.setStatus(NotificationStatus.SENT);
    assertEquals(notification, expected);
    WebNotificationModel model = createModel(entity);
    verify(this.repository).save(model);
    verify(this.publisher).publish("title");
  }

  @Test
  public void notStoreNotificationWhenUserDisabledNotifications() {
    WebNotification entity = createEntity(
        3,
        "awesome",
        new ArrayList<Content>(),
        "newUrl"
    );
    when(this.disableNotification.shouldNotify(entity.getCustomer())).thenReturn(Boolean.FALSE);
    NotificationService<WebNotification> service = new WebNotificationService(
        this.repository,
        this.publisher,
        this.disableNotification
    );
    WebNotification notification = service.store(entity);
    WebNotification expected = createEntity(
        3,
        "awesome",
        new ArrayList<Content>(),
        "newUrl"
    );
    expected.setStatus(NotificationStatus.DISABLED);
    assertEquals(notification, expected);
    verify(this.repository, never()).save(createModel(entity));
    verify(this.publisher, never()).publish("awesome");
    verify(this.disableNotification).shouldNotify(entity.getCustomer());
  }

  @Test
  public void scheduleNotification() {

    WebNotification entity = createEntity(
        5,
        "Check it out",
        new ArrayList<Content>(),
        "url"
    );
    entity.setSchedule("2020-01-03 16:00:00");
    when(this.disableNotification.shouldNotify(entity.getCustomer())).thenReturn(Boolean.TRUE);
    NotificationService<WebNotification> service = new WebNotificationService(
        this.repository,
        this.publisher,
        this.disableNotification
    );
    WebNotification notification = service.schedule(entity);
    WebNotificationModel model = createModel(entity);
    model.setScheduleAt(entity.getSchedule());
    verify(this.repository).save(model);
    verify(this.publisher, never()).publish("Check it out");
    entity.setStatus(NotificationStatus.SCHEDULED);
    assertEquals(notification, entity);
  }

  @Test
  public void notScheduleNotificationWhenUserDisabledNotifications() {
    WebNotification entity = createEntity(
        3,
        "awesome",
        new ArrayList<Content>(),
        "newUrl"
    );
    entity.setSchedule("2020-01-03 16:00:00");
    when(this.disableNotification.shouldNotify(entity.getCustomer())).thenReturn(Boolean.FALSE);
    NotificationService<WebNotification> service = new WebNotificationService(
        this.repository,
        this.publisher,
        this.disableNotification
    );
    WebNotification notification = service.schedule(entity);
    verify(this.repository, never()).save(createModel(entity));
    verify(this.disableNotification).shouldNotify(entity.getCustomer());
    assertEquals(notification, entity);
    entity.setStatus(NotificationStatus.DISABLED);
  }

  private static WebNotificationModel createModel(WebNotification entity) {
    return new WebNotificationModel(
        entity.getCustomer(),
        entity.getTitle(),
        entity.getContents(),
        entity.getRedirectUrl()
    );
  }

  private static WebNotification createEntity(
      Integer identifier,
      String title,
      ArrayList<Content> contents,
      String url
  ) {
    return new WebNotification(
        new Customer(identifier),
        title,
        contents,
        url
    );
  }
}
