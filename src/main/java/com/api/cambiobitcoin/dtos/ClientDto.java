package com.api.cambiobitcoin.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ClientDto {

    @NotEmpty
    @NotNull
    private String fullName;
    @NotEmpty
    @NotNull
    private String cpf;
}
