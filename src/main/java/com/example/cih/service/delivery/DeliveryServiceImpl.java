package com.example.cih.service.delivery;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    //private final DeliveryRepository userRepository;

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
