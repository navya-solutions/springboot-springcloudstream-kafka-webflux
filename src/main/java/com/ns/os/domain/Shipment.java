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
public class Shipment implements Serializable {
    private String id;

    /**
     * Free-form text applying to a shipment. This element may contain notes or any other
     * similar information that is not contained explicitly in another structure.
     */
    private String information;
    /**
     * The total weight of the shipment including packaging, as dispatched from the dispatching party.
     */
    private Measurement grossWeightMeasure;

}
