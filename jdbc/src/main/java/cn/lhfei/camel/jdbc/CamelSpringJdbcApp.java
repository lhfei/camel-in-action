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

package cn.lhfei.camel.jdbc;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 11, 2020
 */
public class CamelSpringJdbcApp {

  private static final Logger LOG = LoggerFactory.getLogger(CamelSpringJdbcApp.class);

  /**
   * Allow this route to be run as an application
   */
  public static void main(String[] args) throws Exception {
    ApplicationContext appContext =
        new ClassPathXmlApplicationContext("classpath:/META-INF/spring/camel-context.xml");
    CamelContext camelContext = new SpringCamelContext(appContext);

    try {
      camelContext.start();
      Thread.sleep(2000);
    } finally {
      camelContext.stop();
    }
  }


}
