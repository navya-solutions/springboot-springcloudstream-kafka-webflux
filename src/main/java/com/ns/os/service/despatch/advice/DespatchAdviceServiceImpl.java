package com.ns.os.service.despatch.advice;

import com.ns.os.domain.DespatchAdvice;
import com.ns.os.exception.DataValidationException;
import com.ns.os.exception.EventAlreadyExistsException;
import com.ns.os.repository.DespatchAdviceRepository;
import com.ns.os.service.customer.CustomerService;
import com.ns.os.service.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DespatchAdviceServiceImpl implements DespatchAdviceService {

    private final SupplierService supplierService;
    private final CustomerService customerService;
    private final DespatchAdviceRepository despatchAdviceRepository;

    @Override
    public Mono<Void> save(DespatchAdvice despatchAdvice) {
        // if despatchAdvice already exist in DB,throw an EventAlreadyExistsException
        return despatchAdviceRepository.existsByIdentifier(despatchAdvice.getIdentifier())
                .flatMap(exists -> (exists) ?
                        Mono.error(new EventAlreadyExistsException(String.format("DespatchAdvice event with identifier %s already store in DB", despatchAdvice.getIdentifier())))
                        : Mono.just(despatchAdvice))
                // validate despatchAdvice
                .map(despatchAdvice1 -> validateDespatchAdviceEvent(despatchAdvice, despatchAdvice1))
                // save supplier anc customer details in db
                .flatMap(despatchAdvice1 -> supplierService.save(despatchAdvice1.getDespatchSupplierParty())
                        .zipWith(customerService.save(despatchAdvice1.getDeliveryCustomerParty()))
                        .zipWith(Mono.just(despatchAdvice1)))
                // update supplier anc customer references
                .map(objects -> {
                    DespatchAdvice objectsT2 = objects.getT2();
                    objectsT2.setDespatchSupplierParty(objects.getT1().getT1());
                    objectsT2.setDeliveryCustomerParty(objects.getT1().getT2());
                    return objectsT2;
                })
                // save event
                .flatMap(despatchAdvice1 -> despatchAdviceRepository.save(despatchAdvice))
                .then();
    }

    @Override
    public Flux<DespatchAdvice> getOrderDespatchAdvices(String orderId, PageRequest pageRequest) {
        return despatchAdviceRepository.findAllByOrderReferenceId(orderId, pageRequest);

    }

    @NotNull
    /**
     * validate -  supplier and customer should be part of the despatch advice
     */
    private DespatchAdvice validateDespatchAdviceEvent(DespatchAdvice despatchAdvice, DespatchAdvice despatchAdvice1) {
        if (despatchAdvice1.getDespatchSupplierParty() == null) {
            throw new DataValidationException
                    (String.format("Supplier details are missing for order reference id %s", despatchAdvice.getOrderReferenceId()));
        }
        if (despatchAdvice1.getDeliveryCustomerParty() == null) {
            throw new DataValidationException
                    (String.format("Customer details are missing for order reference id %s", despatchAdvice.getOrderReferenceId()));
        }
        return despatchAdvice1;
    }


}
