package com.ns.os.integration.service.customer;

import com.ns.os.domain.DeliveryCustomerParty;
import com.ns.os.domain.DespatchAdvice;
import com.ns.os.integration.BaseIT;
import com.ns.os.repository.CustomerRepository;
import com.ns.os.service.customer.CustomerService;
import com.ns.os.util.JsonFileGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerServiceImplTest extends BaseIT {
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    CustomerServiceImplTest(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Test
    void save() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        DeliveryCustomerParty deliveryCustomerParty = despatchAdvice.getDeliveryCustomerParty();
        customerService.save(deliveryCustomerParty)
                .as(StepVerifier::create)
                .assertNext(despatchAdvice1 -> {
                    assertAll(
                            () -> assertNotNull(deliveryCustomerParty.getParty()),
                            () -> assertNotNull(deliveryCustomerParty.getId())

                    );
                })
                .expectComplete()
                .verify();

    }
}