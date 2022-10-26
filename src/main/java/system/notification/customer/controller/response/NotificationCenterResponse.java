package system.notification.customer.controller.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import system.notification.core.entity.Customer;
import system.notification.customer.entity.CustomerNotificationCenter;

@Getter
@EqualsAndHashCode
public class NotificationCenterResponse {
  private final Customer customer;
  private final Boolean notify;

  public NotificationCenterResponse(Customer customer, Boolean notify) {
    this.customer = customer;
    this.notify = notify;
  }

  public static NotificationCenterResponse fromEntity(CustomerNotificationCenter entity) {
    return new NotificationCenterResponse(new Customer(entity.getId()), entity.getNotify());
  }
}
