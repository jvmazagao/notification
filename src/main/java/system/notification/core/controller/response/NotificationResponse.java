package system.notification.core.controller.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import system.notification.core.entity.Customer;

@Getter
@EqualsAndHashCode
public class NotificationResponse {
  private final Customer customer;
  private final String status;

  public NotificationResponse(Customer customer, String status) {
    this.customer = customer;
    this.status = status;
  }
}
