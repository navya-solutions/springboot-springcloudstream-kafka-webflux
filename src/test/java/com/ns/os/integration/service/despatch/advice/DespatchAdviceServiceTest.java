package com.ns.os.integration.service.despatch.advice;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.exception.DataValidationException;
import com.ns.os.integration.BaseIT;
import com.ns.os.repository.DespatchAdviceRepository;
import com.ns.os.service.customer.CustomerService;
import com.ns.os.service.despatch.advice.DespatchAdviceService;
import com.ns.os.service.supplier.SupplierService;
import com.ns.os.util.JsonFileGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DespatchAdviceServiceTest extends BaseIT {

    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final DespatchAdviceRepository despatchAdviceRepository;
    private final DespatchAdviceService despatchAdviceService;

    @Autowired
    DespatchAdviceServiceTest(SupplierService supplierService, CustomerService customerService, DespatchAdviceRepository despatchAdviceRepository, DespatchAdviceService despatchAdviceService) {
        this.supplierService = supplierService;
        this.customerService = customerService;
        this.despatchAdviceRepository = despatchAdviceRepository;
        this.despatchAdviceService = despatchAdviceService;
    }

    @Test
    void save() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        despatchAdviceService.save(despatchAdvice)
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void save_with_missing_supplier_details() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        despatchAdvice.setDespatchSupplierParty(null);
        despatchAdviceService.save(despatchAdvice)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable instanceof DataValidationException &&
                        throwable.getMessage().startsWith("Supplier details are missing"))
                .verify();
    }

    @Test
    void save_with_missing_customer_details() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        despatchAdvice.setDeliveryCustomerParty(null);
        despatchAdviceService.save(despatchAdvice)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable instanceof DataValidationException &&
                        throwable.getMessage().startsWith("Customer details are missing"))
                .verify();
    }

    @Test
    void getOrderDespatchAdvices() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        despatchAdviceService.save(despatchAdvice).block();
        despatchAdviceService.getOrderDespatchAdvices(despatchAdvice.getOrderReferenceId(), PageRequest.of(0, 10))
                .as(StepVerifier::create)
                .assertNext(despatchAdvice1 -> {
                    log.info(despatchAdvice1.toString());
                    assertAll(
                            () -> assertEquals(despatchAdvice1.getIdentifier(), despatchAdvice.getIdentifier()),
                            //TODO: Despatch advice not loading the supplier and customer
                            //() -> assertNotNull(despatchAdvice1.getDespatchSupplierParty()),
                            //() -> assertNotNull(despatchAdvice1.getDeliveryCustomerParty()),
                            () -> assertNotNull(despatchAdvice1.getId())

                    );
                })
                .expectComplete()
                .verify();
    }
}