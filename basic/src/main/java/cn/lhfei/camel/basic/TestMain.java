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

package cn.lhfei.camel.basic;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Jul 31, 2020
 */
public class TestMain {
  private static final Logger LOG = LoggerFactory.getLogger(TestMain.class);
  public static void main(String[] args) {
    Random random = new Random();
    int num = random.nextInt(10) * 5 + 7;
    if((num & 1) == 0) {
      LOG.info("Random even number: [{}]", num);
    }else {
      LOG.info("Random odd number: [{}]", num);
      
    }
    
  }

}
