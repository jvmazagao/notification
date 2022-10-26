package system.notification.web.entity;

import org.junit.jupiter.api.Test;
import system.notification.TestCase;
import system.notification.core.entity.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class WebNotificationTest extends TestCase {
  @Test
  public void testCreation() {
    WebNotification entity = new WebNotification(
        new Customer(1),
        "title",
        new ArrayList<>(),
        "redirect"
    );
    WebNotification entity2 = new WebNotification(
        new Customer(3),
        "another title",
        new ArrayList<>(),
        "url"
    );
    assertNotEquals(entity2, entity);
    assertEquals(entity, new WebNotification(
        new Customer(1),
        "title",
        new ArrayList<>(),
        "redirect"
    ));
    assertNull(entity.getSchedule());
    assertNull(entity2.getSchedule());
  }

  @Test
  public void testSetSchedule() {
    WebNotification entity = new WebNotification(
        new Customer(1),
        "title",
        new ArrayList<>(),
        "redirect"
    );
    entity.setSchedule("2022-03-01 16:00:00");
    assertEquals(entity.getSchedule(),LocalDateTime.parse("2022-03-01T16:00"));
  }
}
