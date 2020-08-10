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

package cn.lhfei.camel.dataformat.processor;

import java.util.Date;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import cn.lhfei.camel.dataformat.model.Order;
import cn.lhfei.camel.dataformat.model.OrderResponse;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 05, 2020
 */
public class OrderProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    Order order = exchange.getIn().getBody(Order.class);
    OrderResponse response = new OrderResponse(order.getOrderId(), true, "", new Date());

    response.setDescription(String.format("Order accepted:[item='%s' quantity='%s']",
        order.getItemId(), order.getQuantity()));
    
    exchange.getMessage().setBody(response);
  }

}
