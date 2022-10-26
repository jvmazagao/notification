package system.notification.core.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import system.notification.core.entity.Customer;

@Data
@EqualsAndHashCode
public class NotificationRequest {
  @JsonProperty("customer")
  private Customer customer;
}
