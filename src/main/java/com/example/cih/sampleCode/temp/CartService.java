package com.example.cih.sampleCode.temp;

import com.example.cih.dto.PageRequestDTO;
import com.example.cih.dto.PageResponseDTO;


public interface CartService {
    PageResponseDTO<CartDTO> getCartAll(PageRequestDTO pageRequestDTO, String userName);
    CartDetailDTO getOrderDetail(Long orderId);
}
