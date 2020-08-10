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

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Jul 31, 2020
 */
public class BasicCamelApp {

  private static CamelContext camel;

  public static void main(String[] args) throws Exception {
    camel = new DefaultCamelContext();

    camel.addRoutes(new RouteBuilder() {

      @Override
      public void configure() throws Exception {
        from("timer:foo").log("Hello Camel");
      }

    });

    // start is not blocking
    camel.start();

    // so run for 10 seconds
    Thread.sleep(5_000);

    // and then stop nicely
    camel.stop();

  }

}
