package system.notification.core.controller;

import org.springframework.web.bind.annotation.RequestBody;
import system.notification.core.controller.request.NotificationRequest;
import system.notification.core.controller.response.NotificationResponse;

public interface NotificationController<T extends NotificationRequest, S extends NotificationResponse> {
  S send (@RequestBody T request);

  S schedule(@RequestBody T request);

}
