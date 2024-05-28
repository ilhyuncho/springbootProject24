package com.example.cih.domain.carConsumable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConsumableType {
    GAS(1), REPAIR(2);

    private final int type;
}
