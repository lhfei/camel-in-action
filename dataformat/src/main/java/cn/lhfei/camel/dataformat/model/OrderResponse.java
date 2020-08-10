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

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Aug 05, 2020
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderResponse {
  @XmlAttribute
  private String orderId;

  @XmlAttribute
  private boolean accepted;

  @XmlAttribute
  private String description;

  @XmlAttribute
  private Date createDate;

  public String getOrderId() {
    return orderId;
  }

  public OrderResponse() {}

  public OrderResponse(String orderId, boolean accepted, String description, Date createDate) {
    this.orderId = orderId;
    this.accepted = accepted;
    this.description = description;
    this.createDate = createDate;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public String toString() {
    return String.format("OrderResponse[orderId='%s', accepted='%s', description='%s']", orderId,
        accepted, description);
  }
}
