package system.notification.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import system.notification.customer.entity.CustomerNotificationCenter;
import system.notification.customer.respository.CustomerRepository;
import system.notification.customer.respository.model.CustomerModel;

import java.util.Optional;
@Component
public class CustomerService {
  private final JpaRepository<CustomerModel, Long> repository;

  @Autowired
  public CustomerService(CustomerRepository repository) {this.repository = repository;}

  public Boolean shouldNotify(Long id) {
    Optional<CustomerModel> model = this.repository.findById(id);
    return model.map(CustomerModel::shouldDeliveryNotification).orElse(Boolean.TRUE);
  }

  public CustomerNotificationCenter store(CustomerNotificationCenter entity) {
    CustomerModel model = this.repository.save(CustomerModel.fromEntity(entity));
    return CustomerNotificationCenter.fromModel(model);
  }
}
