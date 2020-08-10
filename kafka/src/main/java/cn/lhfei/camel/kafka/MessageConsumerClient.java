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

package cn.lhfei.camel.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 10, 2020
 */
public final class MessageConsumerClient {

  private static final Logger LOG = LoggerFactory.getLogger(MessageConsumerClient.class);

  private MessageConsumerClient() {}

  public static void main(String[] args) throws Exception {

    LOG.info("About to run Kafka-camel integration...");

    CamelContext camelContext = new DefaultCamelContext();

    // Add route to send messages to Kafka

    camelContext.addRoutes(new RouteBuilder() {
      public void configure() {
        camelContext.getPropertiesComponent().setLocation("classpath:application.properties");

        log.info("About to start route: Kafka Server -> Log ");

        // setup kafka component with the brokers
        ComponentsBuilderFactory.kafka().brokers("{{kafka.host}}:{{kafka.port}}")
            .register(camelContext, "kafka");

        from("kafka:{{consumer.topic}}" + "&maxPollRecords={{consumer.maxPollRecords}}"
            + "&consumersCount={{consumer.consumersCount}}" + "&seekTo={{consumer.seekTo}}"
            + "&groupId={{consumer.group}}").routeId("FromKafka").log("${body}");
      }
    });
    camelContext.start();

    // let it run for 5 minutes before shutting down
    Thread.sleep(5 * 60 * 1000);

    camelContext.stop();
  }

}
