package com.ns.os.integration.service.customer;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.integration.BaseIT;
import com.ns.os.repository.CustomerRepository;
import com.ns.os.service.customer.CustomerService;
import com.ns.os.util.JsonFileGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

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
        customerService.save(despatchAdvice.getDeliveryCustomerParty())
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}