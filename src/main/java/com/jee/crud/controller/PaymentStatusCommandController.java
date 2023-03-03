package com.jee.crud.controller;

import com.jee.crud.model.PaymentStatusCommand;
import com.jee.crud.repository.PaymentStatusCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status")
public class PaymentStatusCommandController {
    @Autowired
    private PaymentStatusCommandRepository paymentStatusCommandRepository;

    @GetMapping
    public List<PaymentStatusCommand> getAllPaymentStatus() {
        return paymentStatusCommandRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusCommand> getPaymentStatusCommandById(@PathVariable(value = "id") int id) {
        Optional<PaymentStatusCommand> paymentStatusCommand = paymentStatusCommandRepository.findById(id);
        if(paymentStatusCommand.isPresent()) {
            return ResponseEntity.ok().body(paymentStatusCommand.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
