package com.example.cih.dto.history;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryCarDTO {
    private int replacePrice;
    private int accumKm;
    private int gasLitter;
    private String replaceShop;
    private String repairType;
    private LocalDate replaceDate;

    public List<HistoryCarDTO> twice(){
        return List.of(this, this);
    }
}