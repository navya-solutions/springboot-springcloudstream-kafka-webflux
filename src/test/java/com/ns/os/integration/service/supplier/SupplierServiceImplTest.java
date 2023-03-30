package com.ns.os.integration.service.supplier;

import com.ns.os.integration.BaseIT;
import com.ns.os.repository.SupplierRepository;
import com.ns.os.service.supplier.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SupplierServiceImplTest extends BaseIT {
    private final SupplierRepository supplierRepository;
    private final SupplierService supplierService;

    @Autowired
    SupplierServiceImplTest(SupplierRepository supplierRepository, SupplierService supplierService) {
        this.supplierRepository = supplierRepository;
        this.supplierService = supplierService;
    }

    @Test
    void save() {
    }
}