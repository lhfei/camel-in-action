/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.lhfei.camel.basic;

import java.util.Objects;
import java.util.Random;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Jul 31, 2020
 */
public final class Java8App {
  private static final Logger LOGGER = LoggerFactory.getLogger(Java8App.class);

  private Java8App() {
  }

  public static void main(String[] args) throws Exception {
    Main main = new Main();
    main.configure().addRoutesBuilder(new MyRouteBuilder());
    main.run(args);
  }

  private static class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
      from("timer:simple?period=2000")
      .id("simple-route")
      .transform()
          .exchange(this::getRandom)
      .process()
          .message(this::log)
      .process()
          .body(this::log)
      .choice()
          .when()
              .body(Integer.class, b -> (b & 1) == 0)
              .log("Received even number")
          .when()
              .body(Integer.class, (b, h) -> h.containsKey("skip") ? false : (b & 1) == 0)
              .log("Received odd number")
          .when()
              .body(Objects::isNull)
              .log("Received null body")
          .when()
              .body(Integer.class, b -> (b & 1) != 0)
              .log("Received odd number")
      .endChoice();
    }

    private Long getRandom(Exchange e) {
      Random random = new Random();
      int factor = random.nextInt(10) * 100 + 3;
      Long num = Long.parseLong("" + factor);
      LOGGER.info("Random number: [{}], [{}]", factor, num);
      return num;
    }

      
    
//    private Long dateToTime(Exchange e) {
//      return e.getProperty(Exchange.TIMER_FIRED_TIME, Date.class).getTime();
//    }

    private void log(Object b) {
      LOGGER.info("body is: {}", b);
    }

    private void log(Message m) {
      LOGGER.info("message is: {}", m);
    }
  }
}
