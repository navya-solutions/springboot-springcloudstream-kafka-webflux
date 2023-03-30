package com.ns.os.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * The Consignee is the person or organization to which the products will be shipped and who is taking possession.
 * The role is carried out by the customer or on behalf of the customer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("customer")
public class DeliveryCustomerParty implements Serializable {
    @Id
    private String id;

    private Party party;
    private DeliveryContact deliveryContact;


}
