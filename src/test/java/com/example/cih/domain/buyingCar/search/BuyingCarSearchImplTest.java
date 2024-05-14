package com.example.cih.domain.buyingCar.search;

import com.example.cih.domain.buyingCar.BuyingCarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BuyingCarSearchImplTest {

    @Autowired
    private BuyingCarRepository buyingCarRepository;

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("proposalPrice").descending());

        buyingCarRepository.searchTest(pageable);

    }


}