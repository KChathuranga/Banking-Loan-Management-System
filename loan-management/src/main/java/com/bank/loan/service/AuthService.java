package com.bank.loan.service;

import com.bank.loan.dto.LoginRequestDTO;
import com.bank.loan.dto.RegisterRequestDTO;
import com.bank.loan.model.User;

public interface AuthService {

    User register(RegisterRequestDTO dto);

    String login(LoginRequestDTO dto);
}
