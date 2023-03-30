package com.ns.os.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * The Despatch Party is the person or organization who provides (despatch) the goods or services.
 * The role is carried out by the supplier or on behalf of the supplier. (Despatch Party is sometimes known as the Consignor).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("supplier")
public class DespatchSupplierParty implements Serializable {
    @Id
    private String id;
    private Party party;


}
