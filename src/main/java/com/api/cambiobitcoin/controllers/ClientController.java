package com.api.cambiobitcoin.controllers;

import com.api.cambiobitcoin.dtos.ClientDto;
import com.api.cambiobitcoin.models.ClientModel;
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
import java.util.Objects;

@RestController
@RequestMapping("/v1/bitcoin/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api("Client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/{cpf}")
    @ApiOperation("Search client by cpf")
    public ResponseEntity<ClientModel> getClientByCPF(@NotNull @NotEmpty @PathVariable String cpf) {
        ClientModel client = clientService.getClientByCPF(cpf);
        HttpStatus status = Objects.isNull(client) ? HttpStatus.NOT_FOUND: HttpStatus.OK;
        return ResponseEntity.status(status).body(client);
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
}
