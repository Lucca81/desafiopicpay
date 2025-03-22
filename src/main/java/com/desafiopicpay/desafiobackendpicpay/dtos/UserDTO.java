package com.desafiopicpay.desafiobackendpicpay.dtos;

import com.desafiopicpay.desafiobackendpicpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, String email, BigDecimal balance, String password, UserType userType) {

}

