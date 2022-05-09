package com.api.cambiobitcoin.controllers;

import com.api.cambiobitcoin.dtos.ClientDto;
import com.api.cambiobitcoin.models.AccountModel;
import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.services.AccountService;
import com.api.cambiobitcoin.services.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/v1/bitcoin/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api("Client")
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @GetMapping("/{cpf}")
    @ApiOperation("Search client by cpf")
    public ResponseEntity<Map<String, String>> getClientByCPF(@NotNull @NotEmpty @PathVariable String cpf) {
        ClientModel client = clientService.getClientByCPF(cpf);
        AccountModel account;
        HttpStatus status;
        Map<String, String> clientAndAccount = new HashMap<>();
        if (Objects.isNull(client)) {
            status = HttpStatus.NOT_FOUND;
            clientAndAccount.put("message", "Esse cpf não existe em nossa base");

        } else {
            status = HttpStatus.OK;
            account = accountService.getAccountByClient(client);
            clientAndAccount.put("fullName", client.getFullName());
            clientAndAccount.put("cpf", client.getCpf());
            clientAndAccount.put("balance", account.getBalance().toString());
            clientAndAccount.put("BTC", account.getQtdBitcoin().toString());
        }

        return ResponseEntity.status(status).body(clientAndAccount);
    }

    @PostMapping
    public ResponseEntity<ClientModel> createClient(@RequestBody @Valid ClientDto clientDto) {
        ClientModel client = new ClientModel();
        ClientModel newClient = null;
        BeanUtils.copyProperties(clientDto, client);
        HttpStatus status;
        if (clientService.existsClientByCpf(clientDto.getCpf())) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            newClient = clientService.createClient(client);
            status = HttpStatus.CREATED;
        }
        return ResponseEntity.status(status).body(newClient);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deleteClientByCpf(@NotEmpty @NotNull @PathVariable String cpf) {
        boolean existsClient = clientService.existsClientByCpf(cpf);
        HttpStatus status = !existsClient ? HttpStatus.NOT_FOUND: HttpStatus.OK;
        if (existsClient) clientService.deleteClientByCPF(cpf);
        return ResponseEntity.status(status).body("");
    }
}
