package com.example.cih.dto.buyingCar;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString(callSuper = true)
public class BuyingCarListResDTO<E> extends PageResponseDTO<E> {

    int highProposalPrice;

    public BuyingCarListResDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total,
                               int highProposalPrice ) {
        super(pageRequestDTO, dtoList, total);

        this.highProposalPrice = highProposalPrice;
    }
}
