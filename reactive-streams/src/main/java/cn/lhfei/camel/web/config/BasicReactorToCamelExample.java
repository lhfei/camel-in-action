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

package cn.lhfei.camel.web.config;

import java.time.Duration;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.reactive.streams.api.CamelReactiveStreamsService;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Nov 01, 2023
 */
@Configuration
@ConfigurationProperties(prefix = "rstream.basic.reactor-to-camel")
public class BasicReactorToCamelExample {

  /**
   * The reactor streams.
   */
  @Component
  public static class BasicReactorToCamelExampleStreams {

    @Autowired
    private CamelReactiveStreamsService camel;


    @PostConstruct
    public void setupStreams() {

      // Get a subscriber from camel
      Subscriber<String> elements = camel.streamSubscriber("elements", String.class);

      // Emit a string every 7 seconds and push it to the Camel "elements" stream
      Flux.interval(Duration.ofSeconds(7)).map(item -> "element " + item).subscribe(elements);

    }
  }


  /**
   * The Camel Configuration.
   */
  @Component
  public static class BasicReactorToCamelExampleRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      // Transform the body of received items and log
      from("reactive-streams:elements").setBody()
          .simple("BasicReactorToCamel - Camel received ${body}").to("log:INFO");

    }

  }

}
