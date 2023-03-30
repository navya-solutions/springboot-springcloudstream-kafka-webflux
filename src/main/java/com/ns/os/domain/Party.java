package com.ns.os.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Party implements Serializable {
    /**
     * Identifies the despatch party's electronic address
     */
    private String PartyEndpointId;
    /**
     * The identifier of the despatching party
     * <p>
     * Example value: DK88777654
     */
    private String PartyIdentificationId;
    /**
     * PartyLegalEntity- The name of the dispatching party
     */
    private String registrationName;
}
