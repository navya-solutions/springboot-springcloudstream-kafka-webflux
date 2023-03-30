package com.ns.os.service.customer;

import com.ns.os.domain.DeliveryCustomerParty;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<DeliveryCustomerParty> save(final DeliveryCustomerParty customer);


}
