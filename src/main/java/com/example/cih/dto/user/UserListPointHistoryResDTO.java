package com.example.cih.dto.user;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserListPointHistoryResDTO<E> extends PageResponseDTO<E> {

    private final Integer totalMPoint;

    @Builder(builderMethodName = "withSuper")
    public UserListPointHistoryResDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total,
                                      Integer totalMPoint ) {
        super(pageRequestDTO, dtoList, total);

        this.totalMPoint = totalMPoint;
    }
}
