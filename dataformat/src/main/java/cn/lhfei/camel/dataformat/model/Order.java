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

package cn.lhfei.camel.dataformat.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 05, 2020
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord(separator = ",")
public class Order {
  @XmlAttribute
  @DataField(pos = 1)
  private String orderId;

  @XmlAttribute
  @DataField(pos = 2)
  private String itemId;

  @XmlAttribute
  @DataField(pos = 3)
  private int quantity;

  @XmlAttribute
  @DataField(pos = 4)
  private Date createDate;
  
  public Order() {}
  
  public Order(String orderId, String itemId, int quantity, Date createDate) {
    this.orderId = orderId;
    this.itemId = itemId;
    this.quantity = quantity;
    this.createDate = createDate;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return String.format("Order[orderId='%s', itemId='%s', quantity='%s']", orderId, itemId,
        quantity);
  }
}
