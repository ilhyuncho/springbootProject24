package com.example.cih.sampleCode;

import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SampleServiceImpl implements SampleService {

    private final SampleRepository userRepository;

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
