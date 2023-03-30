package com.ns.os.kafka.consumer;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.domain.DespatchAdviceErrorEvent;
import com.ns.os.exception.EventAlreadyExistsException;
import com.ns.os.kafka.producer.DespatchAdviceErrorEventProducer;
import com.ns.os.service.despatch.advice.DespatchAdviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DespatchAdviceEventConsumer {

    private final DespatchAdviceService despatchAdviceService;
    private final DespatchAdviceErrorEventProducer despatchAdviceErrorEventProducer;

    @Bean
    public Function<Flux<List<DespatchAdvice>>, Mono<Void>> processDespatchAdvice() {
        return event -> event
                //.retryWhen(Retry.backoff(3, Duration.ofMillis(1000)))
                .map(despatchAdvice -> {
                    despatchAdvice.forEach(despatchAdvice1 -> {
                        log.info("Data received from order-fulfillment..." + despatchAdvice);
                        despatchAdviceService.save(despatchAdvice1)
                                .onErrorResume(throwable -> fallback(throwable, despatchAdvice1))
                                .subscribeOn(Schedulers.boundedElastic())
                                .subscribe();
                    });
                    return despatchAdvice;
                })
                .then();
    }


    @NotNull
    /**
     * fallback for error handling
     */
    private Mono<Void> fallback(Throwable throwable, DespatchAdvice despatchAdvice) {
        if (throwable instanceof EventAlreadyExistsException) {
            log.warn("========> {}", throwable.getMessage());
        } else {
            log.error("========> {}, {}", throwable.getMessage(), throwable.toString());
            despatchAdviceErrorEventProducer.publishDespatchAdviceErrorEvent(DespatchAdviceErrorEvent.builder()
                            .error(throwable.getMessage())
                            .event(despatchAdvice)
                            .build())
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe();
            return Mono.error(new KafkaException(throwable.getMessage(), throwable));
        }
        return Mono.empty();
    }

}
