package system.notification.customer.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import system.notification.core.entity.Customer;
import system.notification.customer.service.CustomerService;

@Component
public class DisableNotification {
  private final CustomerService service;

  @Autowired
  public DisableNotification(CustomerService service) {
    this.service = service;
  }

  public Boolean shouldNotify(Customer customer) {
    return this.service.shouldNotify(Long.valueOf(customer.getId()));
  }
}
