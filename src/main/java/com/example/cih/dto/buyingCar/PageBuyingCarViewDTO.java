package com.example.cih.dto.buyingCar;
import com.example.cih.dto.PageRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
//@Builder
@NoArgsConstructor
@ToString
public class PageBuyingCarViewDTO {

    List<BuyingCarViewDTO> listBuyingCarDTO = new ArrayList<>();

//    @Builder(builderMethodName = "withAll")
//    //  builder 대신 다름으로 대체. PageResponseDTO.<ReplyDTO>withAll()
//    public PageBuyingCarViewDTO(BuyingCarViewDTO buyingCarViewDTO, List<BuyingCarViewDTO> dtoList){
//
//        this.buyingCarDTO = buyingCarViewDTO;
//        this.listBuyingCarDTO = dtoList;
//    }
}
