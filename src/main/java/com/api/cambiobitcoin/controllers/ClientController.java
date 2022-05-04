package com.api.cambiobitcoin.controllers;

import com.api.cambiobitcoin.models.ClientModel;
import com.api.cambiobitcoin.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@RestController
@RequestMapping("/v1/bitcoin/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

    @Autowired
    ClientService clientService;
    @GetMapping("/{cpf}")
    public ResponseEntity<ClientModel> getClientByCPF(@NotNull @NotEmpty String cpf) {
        ClientModel client = clientService.getClientByCPF(cpf);
        HttpStatus status = Objects.isNull(client) ? HttpStatus.NOT_FOUND: HttpStatus.OK;
        return ResponseEntity.status(status).body(client);
    }
}
