package com.api.cambiobitcoin.utils;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Btc {
    private String base;
    private String currency;
    private BigDecimal amount;
}
