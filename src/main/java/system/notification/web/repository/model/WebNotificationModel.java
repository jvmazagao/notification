package system.notification.web.repository.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import system.notification.core.entity.Customer;
import system.notification.web.entity.Content;
import system.notification.web.entity.WebNotification;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Document(collection = "WebNotification")
@Data
@NoArgsConstructor
public class WebNotificationModel {

  @Id
  private String id;
  private Customer customer;
  private ArrayList<Content> contents;
  private String title;

  private String redirectUrl;

  @Setter
  private LocalDateTime scheduleAt;

  public WebNotificationModel(Customer customer, String title, ArrayList<Content> contents, String redirectUrl) {
    this.customer = customer;
    this.contents = contents;
    this.title = title;
    this.redirectUrl = redirectUrl;
  }

  public static WebNotificationModel fromEntity(WebNotification entity) {
    WebNotificationModel model = new WebNotificationModel(
        entity.getCustomer(),
        entity.getTitle(),
        entity.getContents(),
        entity.getRedirectUrl()
    );
    model.setScheduleAt(entity.getSchedule());
    return model;
  }
}
