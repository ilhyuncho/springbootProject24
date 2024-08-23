package com.example.cih.domain.review;

import com.example.cih.domain.common.BaseEntity;
import com.example.cih.domain.shop.ShopItem;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Reviews")
@ToString
@Log4j2
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reviewId")
    private Long reviewId;

    private String reviewText;
    private String reviewer;

    private int score;

    private Long orderId;
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)   // 일단 @ManyToOne 단방향
    @JoinColumn(name="SHOP_ITEM_ID")
    private ShopItem shopItem;

    @OneToMany(mappedBy = "review", //
            cascade = {CascadeType.ALL}, // Review 엔티티에서 하위 엔티티 객체들을 관리 하는 기능을 추가 해서 사용
            fetch = FetchType.LAZY,
            orphanRemoval = true        // 하위 엔티티가 참조가 더 이상 없는 상태면 삭제 처리 해준다
    )
    @Builder.Default
    @BatchSize(size=20) // N번에 해당하는 쿼리를 모아서 한번에 실행, (N+1문제 해결)
    private Set<ReviewImage> reviewImageSet = new HashSet<>();

    // Review 엔티티 에서 ReviewImage 엔티티 객체들을 모두 관리  begin---------------
    public void addImage(String uuid, String fileName, Boolean isMainImage){

        ReviewImage reviewImage = ReviewImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .review(this)
                .imageOrder(reviewImageSet.size())
                .isMainImage(isMainImage)
                .build();

        reviewImageSet.add(reviewImage);
    }

    public void clearImages(){
        reviewImageSet.forEach(image -> image.changeReview(null));
        this.reviewImageSet.clear();
    }
    public void updateImages(List<String> listFileNames, String mainImageFileName){
        // 초기화
        this.clearImages();

        if(listFileNames.size() > 0){
            listFileNames.forEach(fileName -> {

                String[] index = fileName.split("_");
                addImage(index[0], index[1], (index[1].equals(mainImageFileName)) );
            });
        }

        reviewImageSet.forEach(log::error);
    }
    // Review 엔티티 에서 ReviewImage 엔티티 객체들을 모두 관리 end---------------

}
