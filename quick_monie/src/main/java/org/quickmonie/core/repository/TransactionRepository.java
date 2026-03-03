package org.quickmonie.core.repository;

import org.quickmonie.core.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String > {
}
