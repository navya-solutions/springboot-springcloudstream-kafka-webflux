package com.ns.os.integration.service.supplier;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.domain.DespatchSupplierParty;
import com.ns.os.integration.BaseIT;
import com.ns.os.repository.SupplierRepository;
import com.ns.os.service.supplier.SupplierService;
import com.ns.os.util.JsonFileGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupplierServiceImplTest extends BaseIT {
    private final SupplierRepository supplierRepository;
    private final SupplierService supplierService;

    @Autowired
    SupplierServiceImplTest(SupplierRepository supplierRepository, SupplierService supplierService) {
        this.supplierRepository = supplierRepository;
        this.supplierService = supplierService;
    }

    @Test
    void save() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        DespatchSupplierParty despatchSupplierParty = despatchAdvice.getDespatchSupplierParty();
        supplierService.save(despatchSupplierParty)
                .as(StepVerifier::create)
                .assertNext(despatchAdvice1 -> {
                    assertAll(
                            () -> assertNotNull(despatchSupplierParty.getParty()),
                            () -> assertNotNull(despatchSupplierParty.getId())

                    );
                })
                .expectComplete()
                .verify();
    }
}