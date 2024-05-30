package com.example.cih.common.util;

import java.util.UUID;
import java.util.stream.Stream;

public class Util {

    public static Stream<UUID> createUUID(long count){
        return Stream.generate(UUID::randomUUID)
                .limit(count);
    }
}
