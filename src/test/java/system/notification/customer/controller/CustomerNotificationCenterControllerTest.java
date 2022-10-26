package system.notification.customer.controller;

import org.junit.jupiter.api.Test;
import system.notification.TestCase;
import system.notification.core.entity.Customer;
import system.notification.customer.controller.request.DisableNotificationRequest;
import system.notification.customer.controller.response.NotificationCenterResponse;
import system.notification.customer.entity.CustomerNotificationCenter;
import system.notification.customer.service.CustomerService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerNotificationCenterControllerTest extends TestCase {

  CustomerService service = mock(CustomerService.class);

  @Test
  public void disableNotification() {
    DisableNotificationRequest request = createRequest(1, Boolean.FALSE);
    CustomerNotificationCenter entity = new CustomerNotificationCenter(request.getCustomer(), request.getNotify());
    when(this.service.store(entity)).thenReturn(entity);
    CustomerNotificationCenterController controller = new CustomerNotificationCenterController(this.service);
    NotificationCenterResponse response = controller.disableNotification(request);
    NotificationCenterResponse expected = NotificationCenterResponse.fromEntity(entity);
    assertEquals(response, expected);
    verify(this.service).store(entity);
  }

  private static DisableNotificationRequest createRequest(Integer id, Boolean notify) {
    DisableNotificationRequest request =  new DisableNotificationRequest();
    request.setCustomer(new Customer(id));
    request.setNotify(notify);
    return request;
  }
}
