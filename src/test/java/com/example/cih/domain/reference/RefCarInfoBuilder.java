package com.example.cih.domain.reference;

import java.util.ArrayList;
import java.util.List;

public class RefCarInfoBuilder {


    static CarInfo carInfo = CarInfo.builder().carModel("쏘나타").carGrade("준준형").carOption("옵션3")
            .company("현대").companyNation("대한민국").modelYear(2013).build();
    static CarInfo carInfo1 = CarInfo.builder().carModel("쏘나타2").carGrade("준준형").carOption("옵션2")
            .company("현대").companyNation("대한민국").modelYear(2017).build();
    static CarInfo carInfo2= CarInfo.builder().carModel("쏘나타3").carGrade("준준형").carOption("옵션1")
            .company("현대").companyNation("대한민국").modelYear(2022).build();
    static CarInfo carInfo3= CarInfo.builder().carModel("k5").carGrade("준준형").carOption("옵션1")
            .company("기아").companyNation("대한민국").modelYear(2013).build();
    static CarInfo carInfo4= CarInfo.builder().carModel("k3").carGrade("소형").carOption("옵션1")
            .company("기아").companyNation("대한민국").modelYear(2015).build();
    static CarInfo carInfo5= CarInfo.builder().carModel("k7").carGrade("대형").carOption("옵션1")
            .company("기아").companyNation("대한민국").modelYear(2019).build();


    static CarInfo carInfo6= CarInfo.builder().carModel("e-class").carGrade("준준형").carOption("옵션1")
            .company("벤츠").companyNation("독일").modelYear(2013).build();
    static CarInfo carInfo7= CarInfo.builder().carModel("c-class").carGrade("소형").carOption("옵션1")
            .company("벤츠").companyNation("독일").modelYear(2015).build();
    static CarInfo carInfo8= CarInfo.builder().carModel("e-class").carGrade("대형").carOption("옵션1")
            .company("벤츠").companyNation("독일").modelYear(2019).build();

    static CarInfo carInfo9= CarInfo.builder().carModel("7-series").carGrade("준준형").carOption("옵션1")
            .company("BMW").companyNation("독일").modelYear(2012).build();
    static CarInfo carInfo10= CarInfo.builder().carModel("5-series").carGrade("소형").carOption("옵션1")
            .company("BMW").companyNation("독일").modelYear(2022).build();
    static CarInfo carInfo11= CarInfo.builder().carModel("7-series").carGrade("대형").carOption("옵션1")
            .company("BMW").companyNation("독일").modelYear(2019).build();



    List<CarInfo> carInfoList = new ArrayList<>();



    RefCarInfoBuilder(){
        carInfoList.add(carInfo);
        carInfoList.add(carInfo1);
        carInfoList.add(carInfo2);
        carInfoList.add(carInfo3);
        carInfoList.add(carInfo4);
        carInfoList.add(carInfo5);
        carInfoList.add(carInfo6);
        carInfoList.add(carInfo7);
        carInfoList.add(carInfo8);
        carInfoList.add(carInfo9);
        carInfoList.add(carInfo10);
        carInfoList.add(carInfo11);
    }

}
