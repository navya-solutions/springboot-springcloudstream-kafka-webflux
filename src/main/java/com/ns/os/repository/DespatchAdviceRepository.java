package com.ns.os.repository;

import com.ns.os.domain.DespatchAdvice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface DespatchAdviceRepository extends ReactiveMongoRepository<DespatchAdvice, String> {
    Flux<DespatchAdvice> findAllByOrderReferenceId(String orderId, PageRequest pageRequest);

    Mono<Boolean> existsByIdentifier(String identifier);
}
