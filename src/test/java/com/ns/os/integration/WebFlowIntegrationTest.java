package com.ns.os.integration;

import com.ns.os.AbstractIntegrationTest;
import com.ns.os.domain.DespatchAdvice;
import com.ns.os.util.JsonFileGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Slf4j
@DirtiesContext
public class WebFlowIntegrationTest extends AbstractIntegrationTest {
    private static final String ORDER_DESPATCH_ADVICE_ENDPOINT = "/api/v1/orders/despatch-advices";
    private static final String ORDER_ENDPOINT = "/api/v1/orders";
    public static final String GET_ORDER = ORDER_ENDPOINT + "/{orderRefId}";
    @Autowired
    private WebTestClient webClient;

    /*
    @Mock
    private DespatchAdviceEventConsumer despatchAdviceEventConsumer;
    */


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void routes() {
        //when(despatchAdviceEventConsumer.processDespatchAdvice()).thenReturn(listFlux -> Flux.empty().then());
        webClient.post()
                .uri(ORDER_DESPATCH_ADVICE_ENDPOINT)
                .body(Mono.just(JsonFileGenerator.createDespatchAdvice()), DespatchAdvice.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectStatus()
                .is2xxSuccessful();
    }


}