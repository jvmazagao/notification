package system.notification.web.entity;


import lombok.Getter;

@Getter
public class Content {
  private final String field;
  private final String value;

  public Content(String field, String value) {
    this.field = field;
    this.value = value;
  }
}
