package com.ns.os.kafka.producer;

import com.ns.os.domain.DespatchAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DespatchAdviceEventProducer {

    @Value("${app.order.binding.name}")
    private String orderBinding;
    private final StreamBridge streamBridge;

    /**
     * Publish despatch advice event to kafka topic
     */
    public Mono<Void> publishDespatchAdviceEvent(DespatchAdvice despatchAdvice) {
        log.info(" =====> send event to topic {}", despatchAdvice);
        return Mono.just(MessageBuilder
                        .withPayload(despatchAdvice)
                        .setHeader("source", "rest-event")
                        .build())
                .map(msg -> streamBridge.send(orderBinding, msg))
                .then();

    }


}
