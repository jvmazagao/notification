package system.notification.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.notification.customer.controller.request.DisableNotificationRequest;
import system.notification.customer.controller.response.NotificationCenterResponse;
import system.notification.customer.entity.CustomerNotificationCenter;
import system.notification.customer.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerNotificationCenterController {
  private final CustomerService service;

  @Autowired
  public CustomerNotificationCenterController(CustomerService service) {this.service = service;}

  @PostMapping("/config")
  public NotificationCenterResponse disableNotification(@RequestBody DisableNotificationRequest request) {
    CustomerNotificationCenter entity = new CustomerNotificationCenter(request.getCustomer(),request.getNotify());
    return NotificationCenterResponse.fromEntity(this.service.store(entity));
  }
}
