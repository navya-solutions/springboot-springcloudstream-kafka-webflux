package com.ns.os;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderServiceApplicationTests extends AbstractIntegrationTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    void contextLoads() {
        assertNotNull(ctx);
    }

}
