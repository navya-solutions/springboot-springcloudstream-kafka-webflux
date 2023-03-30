package com.ns.os.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@DirtiesContext
public class WebFlowTest extends BaseIT {
    private static final String ORDER_DESPATCH_ADVICE_ENDPOINT = "/api/v1/orders/despatch-advices";
    private static final String ORDER_ENDPOINT = "/api/v1/orders";
    public static final String GET_ORDER = ORDER_ENDPOINT + "/{orderRefId}";

    private final WebTestClient webClient;

    @Autowired
    public WebFlowTest(WebTestClient webClient) {
        this.webClient = webClient;
    }

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
        /*webClient.post()
                .uri(ORDER_DESPATCH_ADVICE_ENDPOINT)
                .body(Mono.just(JsonFileGenerator.createDespatchAdvice()), DespatchAdvice.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectStatus()
                .is2xxSuccessful();
*/
    }


}