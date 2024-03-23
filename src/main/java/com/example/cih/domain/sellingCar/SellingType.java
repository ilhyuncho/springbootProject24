package com.example.cih.domain.sellingCar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SellingType {
    PRIVATE(1), AUCTION(2);

    private final int type;
}
