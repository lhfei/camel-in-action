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

package cn.lhfei.camel.dataformat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.DataTypeAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.lhfei.camel.dataformat.model.Order;
import cn.lhfei.camel.dataformat.model.OrderResponse;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 05, 2020
 */
public class MainApp {

  private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);
  private static final String CSV_PATH = "target/output/orders.csv";

  private MainApp() {
    // Helper class
  }

  public static void main(final String[] args) throws Exception {
    File csvLogFile = new File(CSV_PATH);
    if (csvLogFile.exists()) {
      LOG.info("---> Removing log file '{}'...", csvLogFile.getAbsolutePath());
      csvLogFile.delete();
    }

    // START SNIPPET: e1
    LOG.info("---> Starting 'order' camel route...");
    try (ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml")) {
      context.start();
      CamelContext camelContext = context.getBean("order", CamelContext.class);
      ProducerTemplate producer = camelContext.createProducerTemplate();
      // END SNIPPET: e1
      Thread.sleep(1000);

//      Order order = new Order("Order-Java-0001", "MILK", 3, new Date());
//      LOG.info("---> Sending '{}' to 'direct:java'", order);
//      OrderResponse response = producer.requestBody("direct:java", order, OrderResponse.class);
//      Thread.sleep(1000);
//      LOG.info("---> Received '{}'", response);
//      LOG.info("---> CSV log now contains:{}", getCsvLog());
//      Thread.sleep(1000);
//
//      String orderXml = "<order orderId=\"Order-XML-0001\" itemId=\"MIKAN\" quantity=\"365\"/>";
//      LOG.info("---> Sending '{}' to 'direct:xml'", orderXml);
//      Exchange answerXml = producer.send("direct:xml", ex -> {
//        ((DataTypeAware) ex.getIn()).setBody(orderXml, new DataType("xml:XMLOrder"));
//      });
//      Thread.sleep(1000);
//      LOG.info("---> Received '{}'", answerXml.getMessage().getBody(String.class));
//      LOG.info("---> CSV log now contains:{}", getCsvLog());
//      Thread.sleep(1000);

      String orderJson =
          "{\"orderId\":\"Order-JSON-0001\", \"itemId\":\"iPhone12-128\", \"quantity\":\"12000\"}";
      LOG.info("---> Sending '{}' to 'direct:json'", orderJson);
      Exchange answerJson = producer.send("direct:json", ex -> {
        ((DataTypeAware) ex.getIn()).setBody(orderJson, new DataType("json"));
      });
      Thread.sleep(1000);
      LOG.info("---> Received '{}'", answerJson.getMessage().getBody(String.class));
      LOG.info("---> CSV log now contains:{}", getCsvLog());
      Thread.sleep(1000);

      context.stop();
    }
  }

  public static String getCsvLog() throws Exception {
    BufferedReader reader = new BufferedReader(new FileReader(new File(CSV_PATH)));
    try {
      StringBuilder buf = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        buf.append(System.lineSeparator()).append(line);
      }
      return buf.toString();
    } finally {
      reader.close();
    }
  }

}

