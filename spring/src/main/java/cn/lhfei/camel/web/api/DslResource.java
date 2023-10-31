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

package cn.lhfei.camel.web.api;

import java.security.SecureRandom;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 *
 * @author Hefei Li
 *
 * @created Aug 16, 2023
 */
@RestController
public class DslResource extends AbstractResource {
  
  @GetMapping("/hello")
  public String sayHello(@RequestParam(defaultValue = "frank") String name) {
    return template.requestBody("direct:sayHello", name).toString();
  }
  
  @GetMapping(value = "/example/{id}")
  public String getRandomString(@PathVariable("id") Integer id) {
    return "Got " + randomString(id);
  }

  String randomString(int len) {
    String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++)
      sb.append(AB.charAt(rnd.nextInt(AB.length())));

    return sb.toString();
  }
  
  @Autowired 
  private ProducerTemplate template;
}
