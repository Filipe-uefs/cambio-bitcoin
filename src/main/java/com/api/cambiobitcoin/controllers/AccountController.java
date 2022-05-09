package com.api.cambiobitcoin.controllers;

import com.api.cambiobitcoin.models.AccountModel;
import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.services.AccountService;
import com.api.cambiobitcoin.services.ClientService;
import com.api.cambiobitcoin.utils.RequestBtc;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/bitcoin/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api("Account")
public class AccountController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @PostMapping("/{cpf}/credit")
    public ResponseEntity<Map<String, String>> addCredit(@RequestBody @NotNull Map<String, Double> mapCredit,
                                                         @PathVariable @NotEmpty @NotNull String cpf) {
        ClientModel client = clientService.getClientByCPF(cpf);
        HttpStatus status;
        Map<String, String> message = new HashMap<>();
        if (Objects.isNull(client)) {
            status = HttpStatus.NOT_FOUND;
            message.put("message", "Esse cpf não existe em nossa base de dados");
        } else if (mapCredit.get("credit") <= 0) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            message.put("message", "Valor a ser creditado deve ser maior que 0");
        } else {
            accountService.updateCredit(accountService.getAccountByClient(client), mapCredit.get("credit"));
            message.put("message", "created");
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(message);
    }

    @PostMapping("/{cpf}/bitcoin")
    public ResponseEntity<Map<String, String>> buyBtc(@PathVariable @NotNull @NotEmpty String cpf,
                                                      @RequestBody @NotNull Map<String, BigDecimal> mapBtc) {
        ClientModel client = clientService.getClientByCPF(cpf);
        HttpStatus status;
        Map<String, String> message = new HashMap<>();
        if (Objects.isNull(client)) {
            status = HttpStatus.NOT_FOUND;
            message.put("message", "Esse cpf não existe em nossa base de dados");
        } else {
            Map<String, String> mapRequestBtc = RequestBtc.getValueBtc();
            AccountModel account = accountService.getAccountByClient(client);
            if (mapRequestBtc.get("response").equals("500")) {
                status = HttpStatus.BAD_GATEWAY;
                message.put("message", "Erro ao tentar buscar dados de btc, tente novamente depois");
            }
            else if (mapBtc.get("btc").compareTo(new BigDecimal(0)) > 0) {
                status = HttpStatus.OK;
                BigDecimal valueToBuy = mapBtc.get("btc")
                        .multiply(new BigDecimal(mapRequestBtc.get("value")));

                if (valueToBuy.compareTo(BigDecimal.valueOf(account.getBalance())) > 0) {
                    message.put("message", "Operação não realizada - Saldo insuficiente");
                } else {
                    account.setBalance(account.getBalance() - valueToBuy.doubleValue());
                    account.setQtdBitcoin(account.getQtdBitcoin().add(mapBtc.get("btc")));
                    accountService.updateCreditAndBtc(account);
                    message.put("message", "Operação realizada com sucesso");
                    message.put("saldo", account.getBalance().toString());
                    message.put("BTC", account.getQtdBitcoin().toString());
                }
            }
            else {
                status = HttpStatus.OK;
                if (account.getQtdBitcoin().compareTo(mapBtc.get("btc").abs()) < 0) {
                    message.put("message", "Operação não realizada - Quantidade de btc em sua conta insuficiente");
                } else {
                    Double newBalance = mapBtc.get("btc").abs()
                            .multiply(new BigDecimal(mapRequestBtc.get("value"))).doubleValue();
                    account.setQtdBitcoin(account.getQtdBitcoin().subtract(mapBtc.get("btc").abs()));
                    account.setBalance(newBalance + account.getBalance());
                    accountService.updateCreditAndBtc(account);
                    message.put("message", "Operação realizada com sucesso");
                    message.put("saldo", account.getBalance().toString());
                    message.put("BTC", account.getQtdBitcoin().toString());
                }
            }
        }
        return ResponseEntity.status(status).body(message);
    }

}
