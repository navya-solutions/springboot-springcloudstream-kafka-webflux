package com.ns.os.repository;

import com.ns.os.domain.DeliveryCustomerParty;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<DeliveryCustomerParty, String> {

}
