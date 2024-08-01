package com.example.cih.domain.shop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {

    @Override
    List<ItemOption> findAllById(Iterable<Long> longs);
}

