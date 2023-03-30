package com.ns.os.api.controller;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.kafka.producer.DespatchAdviceEventProducer;
import com.ns.os.service.despatch.advice.DespatchAdviceService;
import com.ns.os.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class DespatchAdviceHandler {
    private final DespatchAdviceEventProducer despatchAdviceEventProducer;


    private final DespatchAdviceService despatchAdviceService;


    public Mono<ServerResponse> createDespatchAdvice(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(DespatchAdvice.class)
                .flatMap(despatchAdviceEventProducer::publishDespatchAdviceEvent)
                .flatMap(despatchAdvice -> ServerResponse.ok().build());
    }

    public Mono<ServerResponse> getOrderDespatchAdvices(ServerRequest serverRequest) {
        String orderRefId = serverRequest.pathVariable("orderRefId");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(despatchAdviceService.getOrderDespatchAdvices(orderRefId,
                        Pagination.getPageRequest(serverRequest)), DespatchAdvice.class);
    }


}
