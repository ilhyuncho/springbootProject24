package com.example.cih.domain.test;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


public interface CreditCardRepository extends BillingDetailsRepository<CreditCard, Long>{

    List<CreditCard> findByExpYear(String expYear);

}
