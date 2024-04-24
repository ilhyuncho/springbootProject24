package com.example.cih.domain.test;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
@Log4j2
public class MappingInheritanceSpringdataJpaTest {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    @Transactional
    void test1(){
//        CreditCard creditCard = new CreditCard("il hyun","123444367", "10","2040");
//        creditCardRepository.save(creditCard);
//
//        BankAccount bankAccount = new BankAccount("min ho", "343455", "hankook bank", "BANJDF23");
//        bankAccountRepository.save(bankAccount);


        List<CreditCard> ilHyun = creditCardRepository.findByOwner("il hyun");
        assertThat(ilHyun.size()).isEqualTo(2);

        List<BankAccount> banjdf23 = bankAccountRepository.findBySwift("BANJDF23");
        assertThat(banjdf23.get(0).getBankname()).isEqualTo("hankook bank");



    }




}
