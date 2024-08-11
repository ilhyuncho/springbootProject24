package com.example.cih.service.user;

import com.example.cih.common.exception.UserNotFoundException;
import com.example.cih.common.exception.member.MemberExceptions;
import com.example.cih.domain.member.Member;
import com.example.cih.domain.member.MemberRepository;
import com.example.cih.domain.user.User;
import com.example.cih.domain.user.UserGradeType;
import com.example.cih.domain.user.UserRepository;
import com.example.cih.dto.member.MemberJoinDTO;
import com.example.cih.dto.user.UserAddressReqDTO;
import com.example.cih.dto.user.UserDTO;
import com.example.cih.dto.user.UserPasswordReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUser(String memberId) {
        return userRepository.findByMemberId(memberId)
                .orElseThrow(()-> new UserNotFoundException("해당 유저는 존재하지 않습니다"));
    }
    @Override
    public UserDTO findUserDTO(String memberId){
        User user = findUser(memberId);
        return entityToDTO(user);
    }
    @Override
    public Long registerUser(MemberJoinDTO memberJoinDTO) {
        User user = User.builder()
                .userName(memberJoinDTO.getMemberName())    // 유저 이름
                .memberId(memberJoinDTO.getMemberId())
                .mPoint(0)
                .mGrade(UserGradeType.GRADE_A)
                .build();

        return userRepository.save(user).getUserId();
    }
    @Override
    public User registerMainAddress(String memberId, UserAddressReqDTO userAddressReqDTO) {

        User user = findUser(memberId);

        user.registerMainAddress(userAddressReqDTO);

        return user;
    }

    @Override
    public void changePassword(String memberId, UserPasswordReqDTO userPasswordReqDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberExceptions.NOT_FOUND::get);

        boolean matches = passwordEncoder.matches(userPasswordReqDTO.getCurrentPassword(), member.getMemberPw());

        if(matches){
            member.changePassword(passwordEncoder.encode(userPasswordReqDTO.getNewPassword()));
        }
        else{
            throw MemberExceptions.PASSWORD_NOT_SAME.get();
        }
    }
    private static UserDTO entityToDTO(User user) {
        return UserDTO.builder()
                .userID(user.getUserId())
                .userName(user.getUserName())
                .memberId(user.getMemberId())
                .address(user.getAddress() != null ? user.getAddress().fullAddress() : null)
                .billingAddress(user.getBillingAddress() != null ? user.getBillingAddress().fullAddress() : null)
                .mPoint(user.getMPoint())
                .mGrade(user.getMGrade())
                .build();
    }

}
