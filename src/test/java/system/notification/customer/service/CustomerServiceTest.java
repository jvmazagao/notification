package system.notification.customer.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import system.notification.TestCase;
import system.notification.core.entity.Customer;
import system.notification.customer.entity.CustomerNotificationCenter;
import system.notification.customer.respository.CustomerRepository;
import system.notification.customer.respository.model.CustomerModel;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomerServiceTest extends TestCase {

  @Mock
  CustomerRepository repository = mock(CustomerRepository.class);

  @Test
  public void shouldNotify() {
    CustomerService service = new CustomerService(this.repository);
    when(this.repository.findById(1L)).thenReturn(Optional.empty());
    assertTrue(service.shouldNotify(1L));
    verify(this.repository).findById(1L);
  }

  @Test
  public void shouldNotNotify() {
    CustomerService service = new CustomerService(this.repository);
    when(this.repository.findById(3L)).thenReturn(Optional.of(new CustomerModel(3L, false)));
    assertFalse(service.shouldNotify(3L));
    verify(this.repository).findById(3L);
  }

  @Test
  public void shouldStore() {
    CustomerModel model = new CustomerModel(1, false);
    when(this.repository.save(model)).thenReturn(model);
    CustomerService service = new CustomerService(this.repository);
    CustomerNotificationCenter entity = new CustomerNotificationCenter(new Customer(1), Boolean.FALSE);
    CustomerNotificationCenter stored = service.store(entity);
    assertEquals(stored, entity);
  }
}
