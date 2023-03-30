package com.ns.os.domain;

import com.ns.os.util.JsonFileGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DespatchAdviceTest {

    @Test
    void despatchAdvice() {
        DespatchAdvice despatchAdvice = JsonFileGenerator.createDespatchAdvice();
        assertAll(
                () -> assertEquals(despatchAdvice.getCustomizationId(), "urn:fdc:peppol.eu:poacc:trns:despatch_advice:3"),
                () -> assertNotNull(despatchAdvice.getDespatchSupplierParty()),
                () -> assertNotNull(despatchAdvice.getDeliveryCustomerParty()));
    }
}