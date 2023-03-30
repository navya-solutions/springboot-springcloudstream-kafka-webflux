package com.ns.os.service.supplier;

import com.ns.os.domain.DespatchSupplierParty;
import com.ns.os.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {


    private final SupplierRepository supplierRepository;

    @Override
    public Mono<DespatchSupplierParty> save(DespatchSupplierParty supplier) {
        return Mono.just(supplier)
                .flatMap(deliveryCustomerParty -> supplierRepository.save(supplier));

    }

}
