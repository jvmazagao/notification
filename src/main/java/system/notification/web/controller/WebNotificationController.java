package system.notification.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.notification.core.controller.NotificationController;
import system.notification.core.controller.response.NotificationResponse;
import system.notification.core.service.NotificationService;
import system.notification.web.controller.request.WebNotificationRequest;
import system.notification.web.entity.WebNotification;
import system.notification.web.service.WebNotificationService;

@RestController
@RequestMapping("/web")
public class WebNotificationController implements NotificationController<WebNotificationRequest, NotificationResponse> {

  private final NotificationService<WebNotification> service;

  @Autowired
  public WebNotificationController(WebNotificationService service) {
    this.service = service;
  }

  @Override
  @PostMapping("/send")
  public NotificationResponse send(@RequestBody WebNotificationRequest request) {
    WebNotification entity = new WebNotification(
        request.getCustomer(),
        request.getTitle(),
        request.getContents(),
        request.getRedirectUrl()
    );
    WebNotification notification = this.service.store(entity);
    return new NotificationResponse(notification.getCustomer(), notification.getStatus().toString());
  }

  @Override
  @PostMapping("/schedule")
  public NotificationResponse schedule(WebNotificationRequest request) {
    WebNotification entity = new WebNotification(
        request.getCustomer(),
        request.getTitle(),
        request.getContents(),
        request.getRedirectUrl()
    );
    entity.setSchedule(request.getSchedule());
    WebNotification notification = this.service.schedule(entity);
    return new NotificationResponse(notification.getCustomer(), notification.getStatus().toString());
  }
}
