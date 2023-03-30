package com.ns.os.service.customer;

import com.ns.os.domain.DeliveryCustomerParty;
import com.ns.os.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<DeliveryCustomerParty> save(DeliveryCustomerParty customer) {
        return Mono.just(customer)
                .flatMap(deliveryCustomerParty -> customerRepository.save(customer));

    }
}
