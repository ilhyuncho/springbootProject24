package com.example.cih.domain.reference;

import com.example.cih.common.util.Util;
import com.example.cih.domain.car.Car;
import com.example.cih.domain.notification.EventNotification;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class RefCarSampleRepositoryTest {

    @Autowired
    private RefCarSampleRepository refCarSampleRepository;

     String hangul[] = {"가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나", "남", "노", "누", "다",
            "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마", "만", "명", "무", "문", "미", "민", "바", "박",
            "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새", "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순",
            "숭", "슬", "승", "시", "신", "아", "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위",
            "유", "윤", "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종", "주", "준",
            "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판", "하", "한", "해", "혁", "현", "형",
            "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모", "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비",
            "솜", "공", "면", "탁", "온", "디", "항", "후", "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼",
            "열", "웅", "분", "변", "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
            "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향"};

    @Test
    public void insertRefCarSamples(){

        // 차 색상 생성
        Map<Integer, String> mapColor = Map.of(0,"흰색",
                1, "빨강색",
                2,"검은색",
                3,"은색",
                4,"파란색",
                5, "회색");

        // 차 정보 생성
        RefCarInfoBuilder carBuilder = new RefCarInfoBuilder();

        // 임의로 차 넘버 생성  ( Stream 활용 테스트 겸 )
        IntStream randomStream = Util.createRandomStream(100, 100_0000, 999_9999);
        List<String> listCarNumber = randomStream.mapToObj(String::valueOf).map(a -> {
                    StringBuilder buf = new StringBuilder(a);

                    int skipIndex = new Random().nextInt(hangul.length - 1);
                    String temp = Arrays.stream(hangul).skip(skipIndex).findFirst().get();

                    buf.insert(3, temp);
                    return buf.toString();
                })
              //  .peek(log::error)
                .collect(Collectors.toList());



        IntStream.rangeClosed(1,100).forEach(a -> {

            int randomYear = new Random().nextInt(30) + 1990;
            int randomMonth = new Random().nextInt(11) + 1;
            int randomDate = new Random().nextInt(28) + 1;

            int randomKm = new Random().nextInt(200000) + 1000;
            int randomColor= new Random().nextInt(mapColor.size());

            int carInfoIndex = new Random().nextInt(carBuilder.carInfoList.size());
            CarInfo car = carBuilder.carInfoList.get(carInfoIndex);

            RefCarSample refCarSample = RefCarSample.builder()
                    .carNumber(listCarNumber.get(a-1))
                    .carModel(car.getCarModel())
                    .carGrade(car.getCarGrade())
                    .carOption(car.getCarOption())
                    .company(car.getCompany())
                    .companyNation(car.getCompanyNation())
                    .carYear(car.getModelYear())
                    .carKm(randomKm)
                    .carColor(mapColor.get(randomColor))
                    .regDate(LocalDate.of(randomYear,randomMonth,randomDate))
                    .build();

            refCarSampleRepository.save(refCarSample);

        });
    }
}