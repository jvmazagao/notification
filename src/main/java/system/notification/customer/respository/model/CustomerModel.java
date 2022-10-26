package system.notification.customer.respository.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import system.notification.customer.entity.CustomerNotificationCenter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer")
@EqualsAndHashCode
public class CustomerModel {

  @Id
  @Getter
  private long id;

  @Column(name="delivery_notification")
  private boolean deliveryNotification;

  public boolean shouldDeliveryNotification() {
    return deliveryNotification;
  }

  public static CustomerModel fromEntity(CustomerNotificationCenter entity) {
    return new CustomerModel(entity.getId(), entity.getNotify());
  }
}
