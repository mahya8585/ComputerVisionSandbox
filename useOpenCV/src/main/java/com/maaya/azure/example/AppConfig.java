package com.maaya.azure.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;

/**
 * Config
 * Created by maaya
 */
@Configuration
public class AppConfig {

    DataSource dataSource;

    /**
     * リクエストのエンコーディングをUTF-8にします。
     */
    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

//    @Bean
//    public static ConversionService conversionService() {
//        ConversionService conversionService = new DefaultFormattingConversionService();
//        return conversionService;
//    }
}
