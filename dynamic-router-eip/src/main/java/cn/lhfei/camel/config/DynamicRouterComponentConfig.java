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

package cn.lhfei.camel.config;

import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_EIGHTS;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_EVEN;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_FIVES;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_FOURS;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_NINES;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_ODD;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_PRIMES;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_SEVENS;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_SIXES;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_TENS;
import static cn.lhfei.camel.participants.PredicateConstants.PREDICATE_THREES;
import static org.apache.camel.component.dynamicrouter.DynamicRouterConstants.COMPONENT_SCHEME;
import static org.apache.camel.component.dynamicrouter.DynamicRouterConstants.MODE_FIRST_MATCH;
import java.util.concurrent.CountDownLatch;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dynamicrouter.DynamicRouterConfiguration;
import org.apache.camel.component.dynamicrouter.DynamicRouterConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.lhfei.camel.participants.RoutingParticipant;
import cn.lhfei.camel.service.ResultsService;


/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @created Oct 31, 2023
 */
@Configuration
@EnableConfigurationProperties(ExampleConfig.class)
public class DynamicRouterComponentConfig {

    /**
     * Holds the config properties.
     */
    private final ExampleConfig exampleConfig;

    /**
     * Create this config with the config properties object.
     *
     * @param exampleConfig the config properties object
     */
    public DynamicRouterComponentConfig(final ExampleConfig exampleConfig) {
        this.exampleConfig = exampleConfig;
    }

    /**
     * Creates a simple route to allow a producer to send messages through
     * the dynamic router.
     */
    @Bean
    RouteBuilder myRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(exampleConfig.getStartUri())
                        .to(COMPONENT_SCHEME + ":" + exampleConfig.getRoutingChannel() + "?recipientMode=" + exampleConfig.getRecipientMode());
            }
        };
    }

    /**
     * Create a {@link CountDownLatch} so that we can wait on it for the total
     * expected number of messages to be received.  Depending on the configured
     * value of {@link ExampleConfig#getRecipientMode()}, this latch will be
     * created with {@link ExampleConfig#getExpectedAllMatchMessageCount()} or
     * {@link ExampleConfig#getExpectedFirstMatchMessageCount()}.
     *
     * @see DynamicRouterConstants#MODE_ALL_MATCH
     * @see DynamicRouterConstants#MODE_FIRST_MATCH
     * @see DynamicRouterConfiguration#getRecipientMode()
     * @see ExampleConfig#getRecipientMode()
     * @see ExampleConfig#getExpectedAllMatchMessageCount()
     * @see ExampleConfig#getExpectedFirstMatchMessageCount()
     *
     * @return a countdown latch set to the number of expected messages
     */
    @Bean
    CountDownLatch countDownLatch() {
        return new CountDownLatch(MODE_FIRST_MATCH.equals(exampleConfig.getRecipientMode()) ?
                exampleConfig.getExpectedFirstMatchMessageCount() :
                exampleConfig.getExpectedAllMatchMessageCount());
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 10.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant tensRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "tens", 1, PREDICATE_TENS, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 9.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant ninesRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "nines", 2, PREDICATE_NINES, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 8.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant eightsRoutingParticipant(
            final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "eights", 3, PREDICATE_EIGHTS, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 7.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant sevensRoutingParticipant(
            final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "sevens", 4, PREDICATE_SEVENS, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 6.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant sixesRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "sixes", 5, PREDICATE_SIXES, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 5.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant fivesRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "fives", 6, PREDICATE_FIVES, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 4.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant foursRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "fours", 7, PREDICATE_FOURS, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a multiple of 3.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant threesRoutingParticipant(
            final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "threes", 8, PREDICATE_THREES, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * an even number.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant evensRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "even", 9, PREDICATE_EVEN, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * an odd number.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant oddsRoutingParticipant(final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "odd", 100, PREDICATE_ODD, subscriberTemplate, resultsService);
    }

    /**
     * Create a {@link RoutingParticipant} that handles messages where the body is comprised of a number that is
     * a prime.
     *
     * @param subscriberTemplate the {@link ConsumerTemplate} for subscribing to messages that match
     * @param resultsService     the service that handles the results of message processing
     * @return the configured {@link RoutingParticipant}
     */
    @Bean
    RoutingParticipant primesRoutingParticipant(
            final ProducerTemplate subscriberTemplate, final ResultsService resultsService) {
        return new RoutingParticipant(exampleConfig, "primes", 10, PREDICATE_PRIMES, subscriberTemplate, resultsService);
    }
}
