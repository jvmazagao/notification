package system.notification.core.publisher.sns;

import org.springframework.stereotype.Component;
import system.notification.core.publisher.Publisher;

@Component
public class SNSPublisher implements Publisher<String> {

  @Override
  public void publish(String message) {
    System.out.println("sending to sns");
  }
}
