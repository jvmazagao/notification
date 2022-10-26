package system.notification.customer.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import system.notification.core.entity.Customer;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class DisableNotificationRequest {
  private Customer customer;
  private Boolean notify;
}
