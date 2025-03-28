package com.desafiopicpay.desafiobackendpicpay.service;

import com.desafiopicpay.desafiobackendpicpay.domain.transaction.Transaction;
import com.desafiopicpay.desafiobackendpicpay.domain.user.User;
import com.desafiopicpay.desafiobackendpicpay.dtos.TransactionDTO;
import com.desafiopicpay.desafiobackendpicpay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;


    private WebClient webClient;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory2;


    public void createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        boolean isAutorized = !this.authorizeTransaction(sender, transaction.value());
        if (!isAutorized) {
            throw new Exception("Trasação não autorizada");
        }
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);


    }
    public boolean authorizeTransaction(User sender, BigDecimal value) {
        Map<String, Object> response = webClient.get()
                .uri("AUTHORIZE_URL")
                .retrieve()
                .toEntity(Map.class)
                .map(entity -> entity.getStatusCode() == HttpStatus.OK ? entity.getBody() : null )
                .block();
        if (response != null) {
            String message = (String) response.get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        return false;
    }
}
