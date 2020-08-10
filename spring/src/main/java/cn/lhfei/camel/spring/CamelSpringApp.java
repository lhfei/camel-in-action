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

package cn.lhfei.camel.spring;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Jul 31, 2020
 */
public class CamelSpringApp extends RouteBuilder {

  private static final Logger LOG = LoggerFactory.getLogger(CamelSpringApp.class);

  /**
   * Allow this route to be run as an application
   */
  public static void main(String[] args) throws Exception {
    Main main = new Main();
    main.setApplicationContextUri("classpath*:/spring/camel-context.xml");
    main.run(args);
  }

  @Override
  public void configure() {
    // populate the message queue with some messages
    from("file:src/data?noop=true").to("jms:test.MyQueue");

    from("jms:test.MyQueue").to("file://target/test");

    // set up a listener on the file component
    from("file://target/test?noop=true").bean(new SomeBean());
  }

  public static class SomeBean {

    public void someMethod(String body) {
      LOG.info("Received: {}", body);
    }
  }

}
