package com.ns.os.repository;

import com.ns.os.domain.DespatchSupplierParty;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends ReactiveMongoRepository<DespatchSupplierParty, String> {

}
