package com.jee.crud.repository;

import com.jee.crud.model.PaymentStatusCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusCommandRepository extends JpaRepository<PaymentStatusCommand, Integer> {

}
