package system.notification.core.publisher;

public interface Publisher<T> {
  void publish(T message);
}
