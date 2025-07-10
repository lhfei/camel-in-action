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

import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.reactive.streams.util.UnwrapStreamProcessor;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Nov 01, 2023
 */
@Configuration
@ConfigurationProperties(prefix = "rstream.basic.camel-to-reactor-in-out")
public class BasicCamelToReactorInOutExample {

    /**
     * The reactor streams.
     */
    @Component("userBean")
    public static class BasicCamelToReactorInOutExampleStreams {

      /**
       * This method will be called by a Camel route.
       */
      public Publisher<UserInfo> getUserInfo(Publisher<Long> userId) {
        return Flux.from(userId)
            .map(UserInfo::new)
            .flatMap(this::retrieveAddress)
            .flatMap(this::retrieveName);
      }

      /**
       * This is a sample utility method.
       */
      private Publisher<UserInfo> retrieveAddress(UserInfo user) {
          // you can do an async database retrieval here
          return Flux.just(user.withAddress("Address" + user.getId()));
      }

      private Publisher<UserInfo> retrieveName(UserInfo user) {
          // you can do an async database retrieval here
          return Flux.just(user.withName("Name" + user.getId()));
      }

    }


    /**
     * The Camel Configuration.
     */
    @Component
    public static class BasicCamelToReactorInOutExampleRoutes extends RouteBuilder {

      @Override
      public void configure() throws Exception {
  
        // Generate a Id and retrieve user data from reactor
        from("timer:clock?period=9000&delay=1500")
          .setBody().header(Exchange.TIMER_COUNTER).convertBodyTo(List.class) // Sample ID
          .bean("userBean", "getUserInfo") // Get the user info from reactor code
          .process(new UnwrapStreamProcessor()) // Unwrap the Publisher
          .log("BasicCamelToReactorInOut - Got ${body}");
      }

    }

    /**
     * Sample bean used in the example.
     */
    public static class UserInfo {

      private Long id;

      private String name;

      private String address;

      public UserInfo() {}

      public UserInfo(Long id) {
        this.id = id;
      }

      public Long getId() {
        return id;
      }

      public void setId(Long id) {
        this.id = id;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public UserInfo withName(String name) {
        this.name = name;
        return this;
      }

      public String getAddress() {
        return address;
      }

      public void setAddress(String address) {
        this.address = address;
      }

      public UserInfo withAddress(String address) {
        this.address = address;
        return this;
      }

      @Override
      public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
      }
    }
}