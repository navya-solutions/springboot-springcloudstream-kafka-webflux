package com.ns.os.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.os.domain.*;

import java.io.IOException;
import java.util.UUID;

public final class JsonFileGenerator {

    /**
     * Use for creating test json examples from event
     *
     * @param
     */
   /* public static void main(String[] a) {
        objectToJson();
    }*/
    public static void objectToJson() {
        DespatchAdvice despatchAdvice = createDespatchAdvice();
        ObjectMapper Obj = new ObjectMapper();
        try {
            String jsonStr = Obj.writeValueAsString(despatchAdvice);
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DespatchAdvice createDespatchAdvice() {
        DespatchAdvice despatchAdvice = DespatchAdvice.builder()
                .despatchSupplierParty(DespatchSupplierParty.builder()
                        .party(Party.builder()
                                .PartyEndpointId("7385000000124")
                                .PartyIdentificationId("7385000000124")
                                .PartyIdentificationId("Consortial")
                                .build())
                        .build())
                .deliveryCustomerParty(DeliveryCustomerParty.builder()
                        .party(Party.builder()
                                .PartyEndpointId("7390000435951")
                                .PartyIdentificationId("7398000000124")
                                .PartyIdentificationId("IYT Corporation")
                                .build())
                        .deliveryContact(DeliveryContact.builder()
                                .name("Tony Erwing")
                                .electronicMail("tony@buyer.se")
                                .telephone("01272653214")
                                .build())
                        .build())
                .customizationId("urn:fdc:peppol.eu:poacc:trns:despatch_advice:3")
                .note("Despatch advice message")
                .identifier(UUID.randomUUID().toString())
                .orderReferenceId(UUID.randomUUID().toString())
                .profileId("urn:fdc:peppol.eu:poacc:bis:despatch_advice:3")
                .build();
        return despatchAdvice;
    }
}

