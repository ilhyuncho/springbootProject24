package com.example.cih.common.util;

import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {

    public static Stream<UUID> createUUID(long count){
        return Stream.generate(UUID::randomUUID)
                .limit(count);
    }

    public static IntStream createRandomStream(long size, int startIndex, int endIndex  ){
        Random random = new Random();
        // size 개 생성, startIndex ~ endIndex-1 까지

        return random.ints(size, startIndex, endIndex);
    }



}
