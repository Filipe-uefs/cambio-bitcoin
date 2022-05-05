package com.api.cambiobitcoin.controllers;

import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.services.AccountService;
import com.api.cambiobitcoin.services.ClientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
}
