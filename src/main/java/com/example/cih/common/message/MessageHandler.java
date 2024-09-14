package com.example.cih.common.message;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@Log4j2
@AllArgsConstructor
public class MessageHandler {

    private final MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    public String getMessage(MessageCode messageCode, List<String> args){
        //Locale locale = LocaleContextHolder.getLocale();
        //Locale locale = Locale.US;    // 영어 테스트
        //String[] args = {"10"};

        return messageSource.getMessage(messageCode.getMsgCode(), args.toArray(), locale);
    }



}
