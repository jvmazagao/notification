package system.notification.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Customer {

  @JsonProperty("id")
  private Integer id;

  public Customer(Integer id) {
    this.id = id;
  }
}
