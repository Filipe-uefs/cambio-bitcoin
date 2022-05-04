package com.api.cambiobitcoin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bitcoin/customer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {

    @GetMapping
    public ResponseEntity<String> getClientByCPF() {
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
