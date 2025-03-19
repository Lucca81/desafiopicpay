package com.desafiopicpay.desafiobackendpicpay.repositories;

import com.desafiopicpay.desafiobackendpicpay.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
