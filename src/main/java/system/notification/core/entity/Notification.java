package system.notification.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Notification {
  private final Customer customer;
  @Setter private NotificationStatus status;

  public Notification(Customer customer) {
    this.customer = customer;
  }
}
