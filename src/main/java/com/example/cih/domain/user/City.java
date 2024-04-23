package com.example.cih.domain.user;


import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@AllArgsConstructor
@Builder
public class City implements Serializable {

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String cityName;

    @NotEmpty
    private String country;
}
