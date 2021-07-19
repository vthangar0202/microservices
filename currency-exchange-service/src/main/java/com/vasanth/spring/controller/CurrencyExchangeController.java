package com.vasanth.spring.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vasanth.spring.model.CurrencyExchange;
import com.vasanth.spring.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
    
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private Environment environment;

    //http://localhost:8000/currency-exchange/from/USD/to/INR
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        //CurrencyExchange currencyExchange = new CurrencyExchange(1L, from, to, BigDecimal.valueOf(50));
        CurrencyExchange currencyExchange=currencyExchangeRepository.findByFromAndTo(from, to);
        if(currencyExchange==null)
            throw new RuntimeException("Unable to find data from "+from+" to "+to);
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
