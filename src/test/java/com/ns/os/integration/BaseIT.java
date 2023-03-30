package com.ns.os.integration;

import com.ns.os.kafka.consumer.CustomDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Profile("test")
@DirtiesContext
@ActiveProfiles("test")
@Slf4j
@Import(CustomDeserializer.class)
//@EmbeddedKafka(topics = {"order-fulfillment", "order-fulfillment-error"})
public abstract class BaseIT {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    /*@Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
*/

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        log.info("kafka configuration");
        // registry.add("spring.cloud.stream.binder.brokers", () -> kafkaContainer.getHost());
        // registry.add("spring.cloud.stream.binder.defaultBrokerPort", () -> kafkaContainer.getPortBindings());
        registry.add("app.os.binding.name", () -> "order-fulfillment");
        registry.add("app.os.binding.group", () -> "despatch-advice");

        registry.add("spring.cloud.stream.bindings.processDespatchAdvice-in-0.destination", () -> "order-fulfillment");

        registry.add("spring.kafka.consumer.value-deserializer", () -> CustomDeserializer.class);


    }


    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        log.info("database port: {}", mongoDBContainer.getFirstMappedPort());
        registry.add("spring.data.mongodb.database", BaseIT::databaseHost);
        registry.add("spring.data.mongodb.uri", () -> String.format("mongodb://%s:%s", mongoDBContainer.getHost()
                , mongoDBContainer.getFirstMappedPort()));
    }

    static String databaseHost() {
        return "order-fulfilment-test";
    }
}
