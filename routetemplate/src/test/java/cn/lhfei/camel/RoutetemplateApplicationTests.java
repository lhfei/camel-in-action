package cn.lhfei.camel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeUnit;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest(classes = RoutetemplateApplication.class)
class RoutetemplateApplicationTests {

  @Test
  public void shouldProduceMessages() throws Exception {
    // we expect that one or more messages is automatic done by the Camel
    // route as it uses a timer to trigger
    NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();

    assertTrue(notify.matches(10, TimeUnit.SECONDS));
  }
  
  @Test
  public void shouldProduceMessages2() throws Exception {
    // we expect that one or more messages is automatic done by the Camel
    // route as it uses a timer to trigger
    NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();

    assertTrue(notify.matches(10, TimeUnit.SECONDS));
  }

  @Autowired
  private CamelContext camelContext;
}
