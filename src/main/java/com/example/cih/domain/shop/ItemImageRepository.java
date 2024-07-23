package com.example.cih.domain.shop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    List<ItemImage>  findByShopItem(ShopItem shopItem);
}

