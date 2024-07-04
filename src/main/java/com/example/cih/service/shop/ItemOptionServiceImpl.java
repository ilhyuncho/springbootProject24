package com.example.cih.service.shop;


import com.example.cih.domain.shop.ItemOption;
import com.example.cih.domain.shop.ItemOptionRepository;
import com.example.cih.dto.shop.ItemOptionResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ItemOptionServiceImpl implements ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;

    @Override
    public List<ItemOptionResDTO> getListItemOptionInfo(List<Long> listOptionId) {

        return listOptionId.stream()
                .filter(optionId -> optionId != 0)
                .map( optionId -> {
            ItemOption itemOption = itemOptionRepository.findById(optionId)
                    .orElseThrow(() -> new NoSuchElementException("아이템 옵션 정보가 존재하지않습니다"));

            return ItemOptionResDTO.builder()
                    .itemOptionId(itemOption.getItemOptionId())
                    .optionName(itemOption.getOption1())
                    .optionType(itemOption.getType().getName())
                    .build();
        }).collect(Collectors.toList());
    }
}
