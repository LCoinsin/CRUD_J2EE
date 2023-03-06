package com.jee.crud.repository;

import com.jee.crud.model.Command;
import com.jee.crud.model.PaymentStatusCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer> {
    List<Command> findByPaymentStatusCommand(PaymentStatusCommand paymentStatusCommand);
}
