package com.desafiopicpay.desafiobackendpicpay.service;

import com.desafiopicpay.desafiobackendpicpay.domain.user.User;
import com.desafiopicpay.desafiobackendpicpay.domain.user.UserType;
import com.desafiopicpay.desafiobackendpicpay.dtos.UserDTO;
import com.desafiopicpay.desafiobackendpicpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() != UserType.MERCHANT) {
            throw new Exception("Usuario do tipo logista não está autorizado arealizar tranzação");
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");

        }
    }
    public User findUserById(Long id) throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuario não encontrado"));

    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setEmail(data.email());
        newUser.setPassword(data.password());
        newUser.setBalance(data.balance());
        newUser.setDocument(data.document());
        newUser.setUserType(data.userType());

        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }




    public void saveUser(User user){
        this.repository.save(user);
    }
}