package com.example.cih.service.reference;

import com.example.cih.domain.reference.RefCarSample;
import com.example.cih.domain.reference.RefCarSampleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class RefCarSampleServiceImpl implements RefCarSampleService {

    private final RefCarSampleRepository refCarSampleRepository;


    @Override
    public RefCarSample findMyCar(String carNumber) {

        if(carNumber.isEmpty()){
            // 임의로 차량 정보 db에서 get ( 테스트 간편하게... )
            List<RefCarSample> all = refCarSampleRepository.findAll();

            int skipIndex = new Random().nextInt(all.size() - 1);
            RefCarSample refCarSample = all.stream().skip(skipIndex).findFirst().get();
            return refCarSample;
        }
        else{
            Optional<RefCarSample> byCarNumber = refCarSampleRepository.findByCarNumber(carNumber);
            return byCarNumber.orElse(null);
        }
    }
}
