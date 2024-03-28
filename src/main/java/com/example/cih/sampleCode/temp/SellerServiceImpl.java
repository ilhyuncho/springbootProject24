package com.example.cih.sampleCode.temp;

import com.example.cih.sampleCode.SampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

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
