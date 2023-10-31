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

package cn.lhfei.camel.template;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Oct 31, 2023
 */
@org.springframework.stereotype.Component
@Profile("java")
public class MyTemplateBuilder extends RouteBuilder {

  /**
   * Configure and adds routes from route templates.
   */
  @Override
  public void configure() throws Exception {
    // to configure route templates we can use java code as below from a template builder class,
    // gives more power as its java code.
    // or we can configure as well from application.yaml,
    // less power as its key value pair yaml
    // and you can also use both java and yaml together

    // in this example we use properties by default and have disabled java
    templatedRoute("myTemplate")
      .parameter("name", "one")
      .parameter("greeting", "Hello");

    templatedRoute("myTemplate")
      .parameter("name", "deux")
      .parameter("greeting", "Bonjour")
      .parameter("myPeriod", "5s");
  }
}

