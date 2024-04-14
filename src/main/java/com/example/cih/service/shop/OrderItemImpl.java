package com.example.cih.service.shop;

import com.example.cih.domain.shop.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderItemImpl implements OrderItemService {

    private final OrderItemRepository userRepository;

//    @Override
//    public SampleDTO findByUserName(String userName){
//
//        SampleDTO sampleDTO = null;
//
//
//
//        return sampleDTO;
//    }
}
