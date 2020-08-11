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

package cn.lhfei.camel.jdbc.processor;

import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just a demo bean to show we are ready to process each record polled from the DB
 * 
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 11, 2020
 */
public class RecordProcessor implements Processor {

  static Logger LOG = LoggerFactory.getLogger(RecordProcessor.class);

  @Override
  public void process(Exchange msg) {
    LOG.trace("Processing msg {}", msg);
    Map<String, Object> record = msg.getIn().getBody(Map.class);
    LOG.info("======Processing record {}", record);
    // Do something useful with this record.
  }
}
