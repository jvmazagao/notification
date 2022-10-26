package system.notification.web.controller;

import org.junit.jupiter.api.Test;
import system.notification.TestCase;
import system.notification.core.controller.NotificationController;
import system.notification.core.controller.response.NotificationResponse;
import system.notification.core.entity.Customer;
import system.notification.core.entity.NotificationStatus;
import system.notification.web.controller.request.WebNotificationRequest;
import system.notification.web.entity.Content;
import system.notification.web.entity.WebNotification;
import system.notification.web.service.WebNotificationService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WebNotificationControllerTest
    extends TestCase {

  WebNotificationService service = mock(WebNotificationService.class);

  @Test
  public void sendNotification() {
    WebNotificationRequest request = createRequest(1, "awesome notification", "redirectUrl");
    WebNotification entity = createEntity(
        request); WebNotification entityProcessed = createEntity(
        request); entityProcessed.setStatus(NotificationStatus.SENT); when(this.service.store(entity)).thenReturn(entityProcessed);

    NotificationController<WebNotificationRequest, NotificationResponse> webController = new WebNotificationController(service);
    NotificationResponse notification = webController.send(request);
    assertEquals(notification, createResponse(1, "SENT")); verify(this.service).store(entity);
  }

  @Test
  public void sendNotificationAndCallService() {
    WebNotificationRequest request = createRequest(2, "notification", "redirectUrl");
    WebNotification entity = createEntity(request);
    WebNotification entityProcessed = createEntity(request);
    entityProcessed.setStatus(NotificationStatus.DISABLED);

    when(this.service.store(any())).thenReturn(entityProcessed);

    NotificationController<WebNotificationRequest, NotificationResponse> webController = new WebNotificationController(service);
    NotificationResponse notification = webController.send(request);

    assertEquals(notification, createResponse(2, NotificationStatus.DISABLED.toString()));
    verify(this.service).store(entity);
  }

  private static NotificationResponse createResponse(int id, String DISABLED) {
    Customer customer = new Customer();
    customer.setId(id);
    return new NotificationResponse(customer, DISABLED);
  }

  private static WebNotification createEntity(WebNotificationRequest request) {
    return new WebNotification(
        request.getCustomer(),
        request.getTitle(),
        request.getContents(),
        request.getRedirectUrl()
    );
  }

  private static WebNotificationRequest createRequest(int id, String title, String redirectUrl) {
    WebNotificationRequest request = new WebNotificationRequest();
    Customer customer = new Customer();
    customer.setId(id);
    request.setCustomer(customer);
    request.setTitle(title);
    request.setContents(new ArrayList<Content>());
    request.setRedirectUrl(redirectUrl);

    return request;
  }
}
