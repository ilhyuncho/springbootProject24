package com.example.cih.domain.test.case1;

import java.util.List;


public interface CreditCardRepository extends BillingDetailsRepository<CreditCard, Long>{

    List<CreditCard> findByExpYear(String expYear);

}
