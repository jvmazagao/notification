package system.notification.customer.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import system.notification.core.entity.Customer;
import system.notification.customer.respository.model.CustomerModel;

@Getter
@EqualsAndHashCode(callSuper = true)
public class CustomerNotificationCenter extends Customer {
  private final Boolean notify;

  public CustomerNotificationCenter(Customer customer, Boolean notify) {
    super(customer.getId());
    this.notify = notify;
  }

  public static CustomerNotificationCenter fromModel(CustomerModel model) {
    return new CustomerNotificationCenter(new Customer((int) model.getId()), model.shouldDeliveryNotification());
  }
}
