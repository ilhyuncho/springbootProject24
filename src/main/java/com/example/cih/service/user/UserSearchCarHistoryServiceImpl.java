package com.example.cih.service.user;

import com.example.cih.domain.sellingCar.SellingCar;
import com.example.cih.domain.sellingCar.SellingCarStatus;
import com.example.cih.domain.user.*;
import com.example.cih.dto.sellingCar.SellingCarResDTO;
import com.example.cih.service.sellingCar.SellingCarServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserSearchCarHistoryServiceImpl implements UserSearchCarHistoryService{

    private final UserSearchCarHistoryRepository userSearchCarHistoryRepository;


    @Override
    public void insertSearchCarHistory(User user, SellingCar sellingCar) {

        Optional<UserSearchCarHistory> result = userSearchCarHistoryRepository.findByUserAndSellingCar(user, sellingCar);

        if(result.isPresent()){
            UserSearchCarHistory userSearchCarHistory = result.get();

            userSearchCarHistory.plusSearchCount();
        }
        else{
            UserSearchCarHistory userSearchCarHistory = UserSearchCarHistory.builder()
                    .user(user)
                    .sellingCar(sellingCar)
                    .searchCount(1)
                    .build();

            userSearchCarHistoryRepository.save(userSearchCarHistory);
        }
    }

    @Override
    public List<SellingCarResDTO> getSearchCarHistory(User user) {

        List<UserSearchCarHistory> listHistory = userSearchCarHistoryRepository.findByUser(user);

        return listHistory.stream()
                .sorted(Comparator.comparing(UserSearchCarHistory::getModDate))
                .map(UserSearchCarHistory::getSellingCar)
                .filter(sellingCar -> sellingCar.getSellingCarStatus() == SellingCarStatus.PROCESSING)
                .map(SellingCarServiceImpl::entityToDTO)
                .map(SellingCarResDTO -> {     // 대표 이미지만 필터링 ( ImageOrder = 0 )
                    SellingCarResDTO.getFileNames().stream()
                            .filter(carImage -> !carImage.getIsMainImage())
                            .collect(Collectors.toList())
                            .forEach(x -> SellingCarResDTO.getFileNames().remove(x));
                    return SellingCarResDTO;
                })
                .limit(5)
                .collect(Collectors.toList());
    }


}
