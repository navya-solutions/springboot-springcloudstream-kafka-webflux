package com.ns.os.kafka.producer;

import com.ns.os.domain.DespatchAdviceErrorEvent;
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
public class DespatchAdviceErrorEventProducer {

    @Value("${app.error.binding.name}")
    private String orderBinding;
    private final StreamBridge streamBridge;

    /**
     * Publish despatch advice event to kafka topic
     */
    public Mono<Void> publishDespatchAdviceErrorEvent(DespatchAdviceErrorEvent event) {
        log.info(" =====> send event to topic {}", event);
        return Mono.just(MessageBuilder
                        .withPayload(event)
                        .build())
                .map(msg -> streamBridge.send(orderBinding, msg))
                .then();

    }


}
