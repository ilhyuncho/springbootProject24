package com.example.cih.dto;


import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 5;

    private String type;        // 검색의 종류 t,c,w, tc, tw

    private String keyword;

    private String link;

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }

    public String getLink(){

        if (null == link) {
            StringBuilder sb = new StringBuilder();
            sb.append("page=" + this.page);
            sb.append("&size=" + this.size);

            if(this.type != null && this.type.length() > 0){
                sb.append("&type=" + this.type);
            }

            if (keyword != null) {
                sb.append("&keyword=" + URLEncoder.encode(this.keyword, StandardCharsets.UTF_8));
            }
            this.link = sb.toString();
        }

        return this.link;
    }
}
