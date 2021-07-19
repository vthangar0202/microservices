package com.vasanth.spring.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vasanth.spring.model.CurrencyConversion;
import com.vasanth.spring.proxy.CurrencyExchangeProxy;


/**
 * 
 * @author vthangar
 * 
 * Calling another service's REST (Currency-Exchange service) Using Rest template and Feign
 * Currency-conversion service -> Currency-Exchange service -> Database
 */
@RestController
public class CurrencyConversionController {
    
    //REST Template - example
    //http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

        CurrencyConversion currenyConversion = responseEntity.getBody();
        return new CurrencyConversion(currenyConversion.getId(), from, to, quantity,
                currenyConversion.getConversionMultiple(), quantity.multiply(currenyConversion.getConversionMultiple()),
                currenyConversion.getEnvironment());
    }
    
    // Feign example
    
    @Autowired
    CurrencyExchangeProxy currencyExchangeProxy;
    
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {
        CurrencyConversion currenyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
        return new CurrencyConversion(currenyConversion.getId(), from, to, quantity,
                currenyConversion.getConversionMultiple(), quantity.multiply(currenyConversion.getConversionMultiple()),
                currenyConversion.getEnvironment()+" "+"Feign");
    }

}
