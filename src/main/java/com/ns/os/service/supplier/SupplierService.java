package com.ns.os.service.supplier;

import com.ns.os.domain.DespatchSupplierParty;
import reactor.core.publisher.Mono;

public interface SupplierService {
    Mono<DespatchSupplierParty> save(final DespatchSupplierParty supplier);


}
