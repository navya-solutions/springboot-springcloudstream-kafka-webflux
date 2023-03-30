package com.ns.os.service.despatch.advice;

import com.ns.os.domain.DespatchAdvice;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DespatchAdviceService {
    Mono<Void> save(final DespatchAdvice despatchAdvice);

    Flux<DespatchAdvice> getOrderDespatchAdvices(final String orderId, final PageRequest pageRequest);


}
