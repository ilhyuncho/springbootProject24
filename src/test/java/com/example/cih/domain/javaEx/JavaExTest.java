package com.example.cih.domain.javaEx;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaExTest {

    @DisplayName("checkFromToIndex - from ~ to 가 0 ~ length 사이에 없으면 IndexOutOfBoundsException 를 던진다.")
    @Test
    void testCheckFromToIndex2() {
        int fromIndex = 12;
        int toIndex = 23;
        int length = 22;
        //12 ~ 23 은 0 ~ 22 에 속하지 않으므로


        Objects.checkFromToIndex(fromIndex, toIndex, length);

        // 아래는 약간 헷갈림
//        assertThrows(IndexOutOfBoundsException.class,
//                () -> Objects.checkFromToIndex(fromIndex, toIndex, length));
    }

}
