package com.ns.os.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DespatchAdviceErrorEvent {
    private String error;
    private DespatchAdvice event;
}
