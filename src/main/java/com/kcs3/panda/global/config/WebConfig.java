package com.kcs3.panda.global.config;

import com.kcs3.panda.domain.auction.entity.Category;
import com.kcs3.panda.domain.auction.entity.TradingMethod;
import com.kcs3.panda.domain.auction.repository.CategoryRepository;
import com.kcs3.panda.domain.auction.repository.TradingMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TradingMethodRepository tradingMethodRepository;


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToTradingMethodConverter());
        registry.addConverter(new StringToCategoryConverter());
    }

    //거래방법을 id에따라 거래방법이 분류되도록 해주는 컨버터
    private class StringToTradingMethodConverter implements Converter<String, TradingMethod> {
        @Override
        public TradingMethod convert(String source) {
            Long id = Long.parseLong(source);
            Optional<TradingMethod> tradingMethod = tradingMethodRepository.findById(id);
            if (!tradingMethod.isPresent()) {
                throw new IllegalArgumentException("Invalid TradingMethod ID: " + source);
            }
            return tradingMethod.get();
        }
    }


    //카테고리명을 id에따라 카테고리가 분류되도록 해주는 컨버터
    private class StringToCategoryConverter implements Converter<String, Category> {
        @Override
        public Category convert(String source) {
            Optional<Category> category = categoryRepository.findByName(source);
            if (!category.isPresent()) {
                throw new IllegalArgumentException("Invalid category name: " + source);
            }
            return category.get();
        }
    }
}