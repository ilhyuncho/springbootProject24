package com.example.cih.service.shop;


import com.example.cih.dto.shop.ItemOptionResDTO;

import java.util.List;

public interface ItemOptionService {
    List<ItemOptionResDTO> getListItemOptionInfo(List<Long> listOptionId);
}
