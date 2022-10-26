package system.notification.web.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import system.notification.core.controller.request.NotificationRequest;
import system.notification.web.entity.Content;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WebNotificationRequest extends NotificationRequest {
  private ArrayList<Content> contents;
  private String redirectUrl;
  private String title;
  private String schedule;
}
