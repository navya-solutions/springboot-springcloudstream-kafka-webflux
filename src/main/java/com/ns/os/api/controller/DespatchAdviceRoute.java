package com.ns.os.api.controller;

import com.ns.os.domain.DespatchAdvice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * The Despatch Advice message is used in the fulfillment process by the supplier to notify the receiver about
 * the despatch and delivery period for the goods being sent, as well as details about the goods for cross-checking with the order
 * and ultimately the Electronic Despatch Advice is used for declaring how the despatched goods are packed.
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class DespatchAdviceRoute {

    private static final String ORDER_DESPATCH_ADVICE_ENDPOINT = "/api/v1/orders/despatch-advices";
    private static final String ORDER_ENDPOINT = "/api/v1/orders";
    public static final String GET_ORDER = ORDER_ENDPOINT + "/{orderRefId}";


    private final DespatchAdviceHandler handler;

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = ORDER_DESPATCH_ADVICE_ENDPOINT,
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.POST,
                            beanClass = DespatchAdviceHandler.class,
                            beanMethod = "createDespatchAdvice",
                            operation = @Operation(operationId = "createDespatchAdvice",
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "successful operation",
                                                    content = @Content(schema = @Schema())),
                                            @ApiResponse(responseCode = "400", description = "Bad request error")
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(
                                                    schema = @Schema(
                                                            implementation = DespatchAdvice.class
                                                    )
                                            )
                                    ),
                                    tags = {"Despatch Advice"})
                    ),
                    @RouterOperation(path = GET_ORDER,
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            beanClass = DespatchAdviceHandler.class,
                            beanMethod = "getOrderDespatchAdvices",
                            operation = @Operation(operationId = "getOrderDespatchAdvices",
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "successful operation",
                                                    content = @Content(schema = @Schema(implementation = DespatchAdvice[].class))),
                                            @ApiResponse(responseCode = "400", description = "No order found")
                                    },
                                    parameters = {
                                            @Parameter(name = "orderRefId",
                                                    in = ParameterIn.PATH,
                                                    style = ParameterStyle.SIMPLE,
                                                    required = true)
                                    },
                                    tags = {"Order"})
                    )
            })
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route()
                .before(serverRequest -> {
                    log.info("=====> REST REQUEST {}", serverRequest.toString());
                    return serverRequest;
                })
                .POST(ORDER_DESPATCH_ADVICE_ENDPOINT, accept(MediaType.APPLICATION_JSON), handler::createDespatchAdvice)
                .GET(GET_ORDER, accept(MediaType.APPLICATION_JSON), handler::getOrderDespatchAdvices)
                .after((serverRequest, serverResponse) -> {
                    log.info("=====> REST RESPONSE {}", serverResponse.toString());
                    return serverResponse;
                })
                .build();
    }
}


