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
public class MyRouteTemplates extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    // in this example we have created the template in XML and this is disabled
    // create a route template with the given name
    routeTemplate("myTemplate")
      // here we define the required input parameters (can have default values)
      .templateParameter("name")
      .templateParameter("greeting")
      .templateParameter("myPeriod", "3s")
      // here comes the route in the template
      // notice how we use {{name}} to refer to the template parameters
      // we can also use {{propertyName}} to refer to property placeholders
      .from("timer:{{name}}?period={{myPeriod}}")
        .setBody(simple("{{greeting}} {{name}}"))
        .log("Java says ${body}");
    }
}