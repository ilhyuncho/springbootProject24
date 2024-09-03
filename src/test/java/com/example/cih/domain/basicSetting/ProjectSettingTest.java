package com.example.cih.domain.basicSetting;

import com.example.cih.common.util.Util;
import com.example.cih.domain.member.Member;
import com.example.cih.domain.member.MemberRepository;
import com.example.cih.domain.member.MemberRole;
import com.example.cih.domain.reference.*;
import com.example.cih.domain.shop.*;
import com.example.cih.domain.user.*;
import com.example.cih.domain.reference.RefPointSituation;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ProjectSettingTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopItemRepository shopItemRepository;

    @Autowired
    ItemPriceRepository itemPriceRepository;

    @Autowired
    ItemImageRepository itemImageRepository;

    @Autowired
    RefCarConsumableRepository refCarConsumableRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RefPointSituationRepository refPointSituationRepository;

    @Autowired
    RefCarSampleRepository refCarSampleRepository;

    @Autowired
    UserAddressBookRepository userAddressBookRepository;

    @Autowired
    UserAlarmRepository userAlarmRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    int index = 0;

    @Test
    public void InsertUserData(){

        // member 생성
        IntStream.rangeClosed(1, 10).forEach(i -> {

            Member member = Member.builder()
                    .memberId("member" + i)
                    .memberPw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@naver.com")
                    .build();
            member.addRole(MemberRole.USER);

            if (i >= 8) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);

        });

        // zip-code 생성  ( Stream 활용 테스트 겸 )
        IntStream randomStream = Util.createRandomStream(2, 100000, 999999);
        List<String> listZipcode = randomStream.mapToObj(String::valueOf).map(a -> {
            StringBuilder buf = new StringBuilder(a);
            buf.insert(3, "-");
            return buf.toString();
        })
        .peek(log::error)
        .collect(Collectors.toList());

        // User 생성
        IntStream.rangeClosed(1, 10).forEach(i -> {

            City city = new City(listZipcode.get(0), "부천시", "대한민국");
            Address address = Address.builder()
                    .city(city)
                    .street("수도로257번길55")
                    .detailAddress("2동 404호")
                    .build();

            City city1 = new City(listZipcode.get(1), "buchoen", "korea");
            Address address1 = Address.builder()
                    .city(city1)
                    .street("sudoro257")
                    .detailAddress("2dong404ho")
                    .build();

            User user = User.builder()
                    .memberId("member" + i)
                    .userName("김민수" + i)
                    .address(address)
                    .billingAddress(address1)
                    .mPoint(0)
                    .mGrade(UserGradeType.GRADE_E)
                    .build();

            Long userId = userRepository.save(user).getUserId();

            UserAddressBook userAddressBook = UserAddressBook.builder()
                    .user(user)
                    .address(address)
                    .deliveryName("마이홈")
                    .RecipientName("김민수")
                    .deliveryRequest("문앞에 놓아주세요")
                    .RecipientPhoneNumber("01012349482")
                    .isMainAddress(true)
                    .isActive(true)
                    .build();
            userAddressBookRepository.save(userAddressBook);

            // 알림 추가
            UserAlarm userAlarm = UserAlarm.builder()
                    .user(user)
                    .alarmTitle("회원가입을 축하드립니다")
                    .alarmContent("회원가입을 축하드립니다!!! 블라블랑 앞으로 많이 이용해 주세요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
                    .build();
            userAlarmRepository.save(userAlarm);
        });
        
        // 배송 주소록 생성


        // ShopItem 생성
        IntStream.rangeClosed(1,2).forEach(i -> {

            ItemPrice itemPrice = ItemPrice.builder()
                    .originalPrice(10000 * i)
                    .isEventTarget(true)
                    .membershipPercent(10)
                    .build();

            itemPriceRepository.save(itemPrice);

            ShopItem shopItem = ShopItem.builder()
                    .itemName("item" + i)
                    .itemPrice(itemPrice)
                    .stockCount(10000)
                    .purchaseCount(0)
                    .isFreeDelivery(i == 1)
                    .itemTitle("item" + i +"_title")
                    .itemDesc("item" + i +"Desc ~~~~~~~~~~~~!@1231242343546")
                    .build();

//            shopItem.addImage("1a1a1a", "ionic5.png");
//            shopItem.addImage("2a2a2a", "ionic51.png");

            if( i == 1 ){
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_COLOR).typePriority(0).optionName("흰색").optionOrder(0).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_COLOR).typePriority(0).optionName("파랑색").optionOrder(1).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_COLOR).typePriority(0).optionName("검은색").optionOrder(2).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_COLOR).typePriority(0).optionName("빨강색").optionOrder(3).build());

                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_SIZE).typePriority(1).optionName("s 사이즈").optionOrder(0).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_SIZE).typePriority(1).optionName("m 사이즈").optionOrder(1).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_SIZE).typePriority(1).optionName("l 사이즈").optionOrder(2).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_SIZE).typePriority(1).optionName("xl 사이즈").optionOrder(3).build());
            }
            else if( i == 2){
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_TYPE).typePriority(2).optionName("타입1").optionOrder(0).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_TYPE).typePriority(2).optionName("타입2").optionOrder(1).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_TYPE).typePriority(2).optionName("타입3").optionOrder(2).build());
                shopItem.addItemOption(ItemOption.builder()
                        .itemOptionType(ItemOptionType.OPTION_TYPE).typePriority(2).optionName("타입4").optionOrder(3).build());
            }


            shopItemRepository.save(shopItem);

            ItemImage itemImage = ItemImage.builder()
                    .imageOrder(0)
                    .fileName("item" + i + ".png")
                    .uuid("1111")
                    .shopItem(shopItem)
                    .isMainImage(true)
                    .build();
            itemImageRepository.save(itemImage);
        });

        // refCarConsumable 생성
        RefCarConsumable refCarConsumable1 = RefCarConsumable.builder()
                .name("주유")
                .repairType("상시")
                .replaceCycleKm(0)
                .replaceCycleMonth(0)
                .viewOrder(0)
                .build();

        refCarConsumableRepository.save(refCarConsumable1);
        /////////////////////////////////

        // RefPointSituation 생성
        List<RefPointSituation> listRefPointSituation = new ArrayList<>();
        RefPointSituation refPointSituation = RefPointSituation.builder()
                .situationName("첫로그인")
                .situationDesc("첫로그인 설명")
                .AccumCycle(AccumCycle.FIRST_TIME)
                .gainPoint(1000)
                .viewOrder(1)
                .build();
        listRefPointSituation.add(refPointSituation);

        RefPointSituation refPointSituation1 = RefPointSituation.builder()
                .situationName("매일 로그인")
                .situationDesc("매일 로그인 설명")
                .AccumCycle(AccumCycle.EVERYDAY)
                .gainPoint(50)
                .viewOrder(2)
                .build();
        listRefPointSituation.add(refPointSituation1);

        RefPointSituation RefPointSituation2 = RefPointSituation.builder()
                .situationName("내차 등록")
                .situationDesc("내차 등록 설명")
                .AccumCycle(AccumCycle.EACH_ITEM)
                .gainPoint(500)
                .viewOrder(3)
                .build();
        listRefPointSituation.add(RefPointSituation2);

        RefPointSituation refPointSituation3 = RefPointSituation.builder()
                .situationName("차 판매 등록")
                .situationDesc("차 판매 등록 설명")
                .AccumCycle(AccumCycle.EACH_ITEM)
                .gainPoint(400)
                .viewOrder(4)
                .build();
        listRefPointSituation.add(refPointSituation3);

        RefPointSituation refPointSituation4 = RefPointSituation.builder()
                .situationName("차 판매 등록")
                .situationDesc("차 판매 등록 설명")
                .AccumCycle(AccumCycle.EACH_ITEM)
                .gainPoint(400)
                .viewOrder(5)
                .build();
        listRefPointSituation.add(refPointSituation4);

        refPointSituationRepository.saveAll(listRefPointSituation);


        /////////////////////////////////
        // refCarConsumable 생성
        List<String> listName = new ArrayList<>();
        listName.add("엔진오일 및 오일 필터");
        listName.add("에어컨 필터");
        listName.add("브레이크 오일");
        listName.add("에어클리너 필터");
        listName.add("배터리");
        listName.add("점화플러그");
        listName.add("구동 벨트");
        listName.add("파워스티어링 오일");
        listName.add("브레이크 패드 및 디스크");
        listName.add("미션 오일");


        listName.forEach(name -> {
            int random = (int) (Math.random() * 50);

            RefCarConsumable refCarConsumable = RefCarConsumable.builder()
                    .name(name)
                    .repairType( random % 2 == 0 ? "교체" : "점검" )
                    .replaceCycleKm(5000 + (100 * random))
                    .replaceCycleMonth(12 + random)
                    .viewOrder(index++)
                    .build();

            refCarConsumableRepository.save(refCarConsumable);
        });

    }

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
