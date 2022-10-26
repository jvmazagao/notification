package system.notification.web.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import system.notification.core.entity.Customer;
import system.notification.core.entity.Notification;
import system.notification.web.repository.model.WebNotificationModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Getter
@EqualsAndHashCode(callSuper = true)
public class WebNotification extends Notification {

  private final ArrayList<Content> contents;
  private final String redirectUrl;
  private final String title;

  private LocalDateTime schedule;
  public WebNotification(
      Customer customer,
      String title,
      ArrayList<Content> contents,
      String redirectUrl
  ) {
    super(customer);
    this.contents = contents;
    this.redirectUrl = redirectUrl;
    this.title = title;
  }

  public void setSchedule(String schedule) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    this.schedule = LocalDateTime.parse(schedule, formatter);
  }
}
