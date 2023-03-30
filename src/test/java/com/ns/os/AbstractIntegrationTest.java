package com.ns.os;

import com.ns.os.kafka.consumer.CustomDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Profile("test")
@DirtiesContext
@ActiveProfiles("test")
@Slf4j
@Import(CustomDeserializer.class)
public abstract class AbstractIntegrationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));


    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        log.info("kafkaContainer host: {}", kafkaContainer.getHost());
        kafkaContainer.addEnv(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.ns.os.kafka.consumer.CustomDeserializer");
        kafkaContainer.addEnv(ConsumerConfig.GROUP_ID_CONFIG, "despatch-advice");
        kafkaContainer.addEnv(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "true");
        kafkaContainer.addEnv("KAFKA_CREATE_TOPICS", "order-fulfillment");
        kafkaContainer.getEnvMap().entrySet().forEach(entrySet -> log.info("{}->{}", entrySet.getKey(), entrySet.getValue()));


        registry.add(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, () -> "com.ns.os.kafka.consumer.CustomDeserializer");
        registry.add(ConsumerConfig.GROUP_ID_CONFIG, () -> "despatch-advice");
        registry.add(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, () -> "true");
        registry.add("KAFKA_CREATE_TOPICS", () -> "order-fulfillment");

        registry.add("spring.cloud.stream.binder.brokers", () -> kafkaContainer.getHost());
        registry.add("spring.cloud.stream.binder.defaultBrokerPort", () -> kafkaContainer.getPortBindings());
        registry.add("app.os.binding.name", () -> "order-fulfillment");
        registry.add("app.os.binding.group", () -> "despatch-advice");

        registry.add("spring.cloud.stream.bindings.processDespatchAdvice-in-0.destination", () -> "order-fulfillment");

        registry.add("spring.kafka.consumer.value-deserializer", () -> CustomDeserializer.class);


        kafkaContainer.getEnvMap().entrySet().forEach(entrySet -> log.info("{}->{}", entrySet.getKey(), entrySet.getValue()));


        kafkaContainer.waitingFor(Wait.forLogMessage("Created topic .*", 3)
                .withStartupTimeout(Duration.ofSeconds(180)));


    }


    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        log.info("database port: {}", mongoDBContainer.getFirstMappedPort());
        registry.add("spring.data.mongodb.database", AbstractIntegrationTest::databaseHost);
        registry.add("spring.data.mongodb.uri", () -> String.format("mongodb://%s:%s", mongoDBContainer.getHost()
                , mongoDBContainer.getFirstMappedPort()));
    }

    static String databaseHost() {
        return "order-fulfilment-test";
    }
}
