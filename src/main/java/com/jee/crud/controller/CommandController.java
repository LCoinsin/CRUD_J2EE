package com.jee.crud.controller;

import com.jee.crud.model.Command;
import com.jee.crud.model.PaymentStatusCommand;
import com.jee.crud.repository.CommandRepository;
import com.jee.crud.repository.PaymentStatusCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/command")
public class CommandController {
    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private PaymentStatusCommandRepository paymentStatusCommandRepository;

    @GetMapping
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @GetMapping("/{command}")
    public List<Command> getCommandsByPaymentStatusCommandId(@PathVariable int command) {
        PaymentStatusCommand paymentStatusCommand = paymentStatusCommandRepository.findById(command).orElse(null);
        if (paymentStatusCommand != null) {
            return commandRepository.findByPaymentStatusCommand(paymentStatusCommand);
        } else {
            return null;
        }
    }
}
