package com.example.cih.domain.car;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsumableType {
    GAS(1L), REPAIR(2L);

    private final Long type;
}
