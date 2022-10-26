package system.notification.core.service;

import system.notification.core.entity.Notification;

public interface NotificationService<T extends Notification> {
  T store(T entity);
  T schedule(T entity);
}
