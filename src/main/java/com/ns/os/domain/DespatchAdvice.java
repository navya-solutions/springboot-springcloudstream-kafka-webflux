package com.ns.os.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("despatch-advice")
public class DespatchAdvice implements Serializable {

    @Id
    private String id;

    /**
     * Identifies the specification of content and rules that apply to the transaction.
     * <p>
     * Example value: urn:fdc:peppol.eu:poacc:trns:despatch_advice:3
     */
    private String customizationId;
    /**
     * Identifies the BII profile or business process context in which the transaction appears.
     * <p>
     * Fixed value: urn:fdc:peppol.eu:poacc:bis:despatch_advice:3
     */
    private String profileId;
    /**
     * A transaction instance must contain an identifier.
     * The identifier enables positive referencing the document instance for various purposes
     * including referencing between transactions that are part of the same process.
     * <p>
     * Example value: 123
     */

    private String identifier;
    /**
     * A textual note for the dispatch as whole.
     */

    private String note;
    /**
     * Used to provide a reference to the order on which the despatch is based.
     */
    private String orderReferenceId;

    @DocumentReference
    private DespatchSupplierParty despatchSupplierParty;

    @DocumentReference
    private DeliveryCustomerParty deliveryCustomerParty;
}
