package com.ns.os.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.os.domain.DespatchAdviceErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class CustomErrorSerializer implements Serializer<DespatchAdviceErrorEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, DespatchAdviceErrorEvent data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            log.info("=====> Serializing DespatchAdviceErrorEvent...");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing DespatchAdviceErrorEvent to byte[]");
        }
    }


    @Override
    public byte[] serialize(String topic, Headers headers, DespatchAdviceErrorEvent data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
