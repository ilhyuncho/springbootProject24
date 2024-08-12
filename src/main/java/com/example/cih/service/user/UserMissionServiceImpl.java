package com.example.cih.service.user;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.domain.reference.RefMission;
import com.example.cih.domain.reference.RefMissionRepository;
import com.example.cih.domain.user.*;
import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.user.UserMissionListResDTO;
import com.example.cih.dto.user.UserMissionReqDTO;
import com.example.cih.dto.user.UserMissionResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserMissionServiceImpl implements UserMissionService{

    private final UserMissionRepository userMissionRepository;
    private final RefMissionRepository refMissionRepository;
    private final UserService userService;

    @Override
    public void insertUserMission(String memberId, UserActionType userActionType, String...varCheckValue) {

        User user = userService.findUser(memberId);

        String checkValue = Arrays.stream(varCheckValue).findFirst().orElse(null);

        RefMission refMission = checkMissionIncomplete(user, userActionType, checkValue);

        if(refMission != null){
            log.error("refMission.getRefMissionId() : " + refMission.getRefMissionId().toString());
            log.error("RefMissionType.fromValue() : " + RefMissionType.fromValue(refMission.getRefMissionId()));

            user.addMPoint(refMission.getGainPoint());

            userMissionRepository.save(UserMission.builder()
                    .user(user)
                    .refMissionType(RefMissionType.fromValue(refMission.getRefMissionId()))
                    .gainPoint(refMission.getGainPoint())
                    .checkValue(checkValue)
                    .build());
        }
    }

    @Override
    public RefMission checkMissionIncomplete(User user, UserActionType userActionType, String checkValue ) {

        List<UserMission> userMission = userMissionRepository.findByUser(user);

        RefMission refMission = null;

        if(userActionType.equals(UserActionType.ACTION_LOGIN)){
            if( userMission.size() == 0){
               
                refMission = refMissionRepository.findById(RefMissionType.FIRST_LOGIN.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
                log.error("최초 로그인");
            }
            else{
                long count = userMission.stream()
                        .filter(mission -> mission.getRefMissionType().equals(RefMissionType.DAILY_LOGIN)
                                || mission.getRefMissionType().equals(RefMissionType.FIRST_LOGIN))
                        .filter(mission -> mission.getRegDate().toLocalDate().equals(LocalDate.now()))
                        .count();

                if(count == 0){
                    refMission = refMissionRepository.findById(RefMissionType.DAILY_LOGIN.getType())
                            .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
                    log.error("오늘 출석");
                }
            }
        }
        else if(userActionType.equals(UserActionType.ACTION_REG_MY_CAR)){

            if(checkValue == null){
                return null;
            }
            long count = userMission.stream()
                    .filter(mission -> mission.getRefMissionType().equals(RefMissionType.REGISTER_CAR))
                    .filter(mission -> mission.getCheckValue().equals(checkValue)).count();

            if(count == 0){
                refMission = refMissionRepository.findById(RefMissionType.REGISTER_CAR.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
            }
            else{
                log.error("이미 미션 포인트를 받은 차량 등록 임!!");
            }
        }
        else if(userActionType.equals(UserActionType.ACTION_REG_SELLING_CAR)){
            if(checkValue == null){
                return null;
            }
            long count = userMission.stream()
                    .filter(mission -> mission.getRefMissionType().equals(RefMissionType.SELL_CAR))
                    .filter(mission -> mission.getCheckValue().equals(checkValue)).count();

            if(count == 0){
                refMission = refMissionRepository.findById(RefMissionType.SELL_CAR.getType())
                        .orElseThrow(() -> new NoSuchElementException("해당 미션 정보가 존재하지않습니다"));
            }
            else{
                log.error("이미 미션 포인트를 받은 차량 판매 임!!");
            }
        }

        return refMission;
    }

    @Override
    public UserMissionListResDTO<UserMissionResDTO> getListUserMission(PageRequestDTO pageRequestDTO,
                                                      User user, UserMissionReqDTO userMissionReqDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("regDate");

        Page<UserMission> result = userMissionRepository.searchUserMission(types, keyword, pageable, userMissionReqDTO);

        // Page는 map을 지원해서 내부 데이터를 다른것으로 변경 가능
        List<UserMissionResDTO> dtoList = result.map(UserMissionServiceImpl::entityToDTO)
                .stream().collect(Collectors.toList());

//        List<UserMission> userMissionList = result.getContent();
//        List<UserMissionResDTO> dtoList = userMissionList.stream()
//                .map(UserMissionServiceImpl::entityToDTO).collect(Collectors.toList());

        // List.copyOf 활용 예 ( 불변 객체 리턴 )
        //List<UserMissionResDTO> unModifyCartDTOList = List.copyOf(dtoList);

        // 에러 발생!!!
        //unModifyCartDTOList.add(CartResponseDTO.builder().build());

        // 하지만 list안이 객체라면 수정 가능 하다..( 주의 해서 사용해야 함 )
        //unModifyCartDTOList.get(0).setItemName("update Temp");

        return new UserMissionListResDTO<UserMissionResDTO>(
                pageRequestDTO, dtoList,
                (int)result.getTotalElements(), user.getMPoint());
//        return PageResponseDTO.<UserMissionResDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
    }

    private static UserMissionResDTO entityToDTO(UserMission userMission) {

        return UserMissionResDTO.builder()
                .refMissionName(userMission.getRefMissionType().getTypeName())
                .gainPoint(userMission.getGainPoint())
                .regDate(userMission.getRegDate().toLocalDate())
                .build();
    }
}
