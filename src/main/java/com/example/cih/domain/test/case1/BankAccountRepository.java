package com.example.cih.domain.test.case1;

import java.util.List;


public interface BankAccountRepository extends BillingDetailsRepository<BankAccount, Long>{

    List<BankAccount> findBySwift(String swift);

}
