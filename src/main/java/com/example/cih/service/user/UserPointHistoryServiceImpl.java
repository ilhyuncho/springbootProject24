package com.example.cih.service.user;

import com.example.cih.domain.reference.RefPointSituation;
import com.example.cih.domain.reference.RefPointSituationRepository;
import com.example.cih.domain.user.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserPointHistoryServiceImpl implements UserPointHistoryService {

    private final UserPointHistoryRepository userPointHistoryRepository;
    private final RefPointSituationRepository refPointSituationRepository;
    private final UserService userService;

    @Override
    public void gainUserPoint(String memberId, UserActionType userActionType, String...varCheckValue) {

        User user = userService.findUser(memberId);

        String checkValue = Arrays.stream(varCheckValue)
                .findFirst()
                .orElse(null);  // 예) 차량 판매 시 판매 하는 차량 번호

        RefPointSituation refPointSituation = checkMissionIncomplete(user, userActionType, checkValue);

        if(refPointSituation != null){

            user.addMPoint(refPointSituation.getGainPoint());

            userPointHistoryRepository.save(UserPointHistory.builder()
                    .user(user)
                    .pointSituation(PointSituation.fromValue(refPointSituation.getRefPointSituationId()))
                    .pointValue(refPointSituation.getGainPoint())
                    .checkValue(checkValue)
                    .build());
        }
    }

    @Override
    public void consumeUserPoint(String memberId, UserActionType userActionType, int consumePoint){

        User user = userService.findUser(memberId);

        userPointHistoryRepository.save(UserPointHistory.builder()
                .user(user)
                .pointSituation(PointSituation.BUY_ITEM_WITH_POINT)
                .pointValue(consumePoint)
                .build());
    }

    @Override
    public RefPointSituation checkMissionIncomplete(User user, UserActionType userActionType, String checkValue) {

        log.error("checkMissionIncomplete()!" + userActionType + "," + checkValue );
        // 총 성공 미션 갯수 get
        int countUserPointHistory = userPointHistoryRepository.getCountUserPointHistory(user);

        if(countUserPointHistory == 0 && userActionType.equals(UserActionType.ACTION_LOGIN)){

            return refPointSituationRepository.findById(PointSituation.FIRST_LOGIN.getType())
                    .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
        }
        else{
            PointSituation pointSituation = convertPointSituation(userActionType);

            List<UserPointHistory> listUserPointHistory = userPointHistoryRepository.getListPointHistoryBySituationType(user, pointSituation, checkValue);
            if(listUserPointHistory.size() == 0){

                return refPointSituationRepository.findById(pointSituation.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
            }

            listUserPointHistory.forEach(log::error);
        }
        return null;
    }

    @Override
    public UserListPointHistoryResDTO<UserPointHistoryResDTO> getListUserPointHistory(PageRequestDTO pageRequestDTO,
                                                                                      User user, UserPointHistoryReqDTO userPointHistoryReqDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

        Page<UserPointHistory> result = userPointHistoryRepository.searchUserPointHistory(types, keyword, pageable, userPointHistoryReqDTO);

        // Page는 map을 지원해서 내부 데이터를 다른것으로 변경 가능
        List<UserPointHistoryResDTO> dtoList = result.map(UserPointHistoryServiceImpl::entityToDTO)
                .stream().collect(Collectors.toList());

        dtoList.forEach(log::error);
        // List.copyOf 활용 예 ( 불변 객체 리턴 )
        //List<UserMissionResDTO> unModifyCartDTOList = List.copyOf(dtoList);

        // 에러 발생!!!
        //unModifyCartDTOList.add(CartResponseDTO.builder().build());

        // 하지만 list안이 객체라면 수정 가능 하다..( 주의 해서 사용해야 함 )
        //unModifyCartDTOList.get(0).setItemName("update Temp");

        return new UserListPointHistoryResDTO<UserPointHistoryResDTO>(
                pageRequestDTO, dtoList,
                (int)result.getTotalElements(), user.getMPoint());
//        return PageResponseDTO.<UserMissionResDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
    }

    public static PointSituation convertPointSituation(UserActionType userActionType){
        switch (userActionType) {
            case ACTION_LOGIN:
                return PointSituation.DAILY_LOGIN;
            case ACTION_REG_MY_CAR:
                return PointSituation.REGISTER_CAR;
            case ACTION_REG_SELLING_CAR:
                return PointSituation.SELL_CAR;
            default:
                return PointSituation.MISSION_NONE;
        }
    }

    private static UserPointHistoryResDTO entityToDTO(UserPointHistory userPointHistory) {

        return UserPointHistoryResDTO.builder()
                .situationName(userPointHistory.getPointSituation().getTypeName())
                .pointValue(userPointHistory.getPointValue())
                .regDate(userPointHistory.getRegDate().toLocalDate())
                .build();
    }
}
