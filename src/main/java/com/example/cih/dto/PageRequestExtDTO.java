package com.example.cih.dto;


import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PageRequestExtDTO extends PageRequestDTO{
    public PageRequestExtDTO(int page, int size, String type, String keyword, String link, String typeExt) {
        super(page, size, type, keyword, link);
        this.typeExt = typeExt;
    }

    private String typeExt;        // 검색의 종류 a(경매), c(상담)
    public String[] getTypeExts(){
        if(typeExt == null || typeExt.isEmpty()){
            return null;
        }
        return typeExt.split("");
    }


}
