/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.lhfei.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @created Aug 16, 2023
 */
@Component
public class RestRoute extends RouteBuilder {

// sample 1: 
//    @Override
//    public void configure() throws Exception {
//      restConfiguration().host("localhost").port(6310);
//      from("timer:hello?period={{timer.period}}")
//        .setHeader("id", simple("${random(6,9)}"))
//        .to("rest:get:api/v1/example/{id}")
//        .log("${body}");
//    }
    
  // sample 2:  uses a transformer to log some text messages
//    @Override
//    public void configure() {
//      from("timer:hello?period={{timer.period}}")
//        .routeId("hello").transform()
//        .method("myBean", "saySomething")
//        .filter(simple("${body} contains 'foo'"))
//        .to("log:foo")
//        .end();
//    }
    
    // sample 3: replace the timer component with “direct:sayHello“:
    @Override
    public void configure() {
      from("direct:sayHello")
          .routeId("hello")
          .transform()
          .method("myBean", "saySomething")
          .filter(simple("${body} contains 'foo'"))
          .to("log:foo")
          .end();
    }
}
