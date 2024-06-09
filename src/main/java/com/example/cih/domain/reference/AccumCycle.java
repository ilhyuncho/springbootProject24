package com.example.cih.domain.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccumCycle {
    FIRST_TIME(0), EVERYDAY(1), EACH_ITEM(2);

    private final int type;
}
