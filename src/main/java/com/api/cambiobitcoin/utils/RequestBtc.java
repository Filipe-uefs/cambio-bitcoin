package com.api.cambiobitcoin.utils;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestBtc {

    public static Map<String, String> getValueBtc() {
        String url = "https://api.coinbase.com/v2/prices/spot?currency=BRL";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> mapResponse = new HashMap<>();
        Data dataBtc = restTemplate.getForObject(url, Data.class);
        if (!Objects.isNull(dataBtc)) {
            Btc btc = dataBtc.getData();
            mapResponse.put("response", "200");
            mapResponse.put("value", btc.getAmount().toString());
        } else {
            mapResponse.put("response", "500");
        }
        return mapResponse;
    }
}
