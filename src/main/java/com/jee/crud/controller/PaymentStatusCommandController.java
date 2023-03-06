package com.jee.crud.controller;

import com.jee.crud.model.PaymentStatusCommand;
import com.jee.crud.repository.PaymentStatusCommandRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @PostMapping("/{id}")
    public ResponseEntity<String> updateStatusPayment(@RequestBody String commandJson, @PathVariable(value = "id") int id) {
        PaymentStatusCommand paymentStatusCommand = paymentStatusCommandRepository.findById(id).orElse(null);
        
        // parsing file "JSONExample.json"
        Object obj;
        boolean bool = false;
		try {
			obj = new JSONParser().parse(commandJson);
			JSONObject jo = (JSONObject) obj;
			bool = (boolean) jo.get("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok("JSON is not correctly set. 'status' data must be set with a boolean value.");
		}

        if(paymentStatusCommand!=null) {
        	paymentStatusCommand.setState(bool);
        	paymentStatusCommandRepository.save(paymentStatusCommand);
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/switch")
    public ResponseEntity<String> switchStatusPayment(@PathVariable(value = "id") int id) {
        PaymentStatusCommand paymentStatusCommand = paymentStatusCommandRepository.findById(id).orElse(null);
        if(paymentStatusCommand!=null) {
        	paymentStatusCommand.setState(!paymentStatusCommand.isState());
        	paymentStatusCommandRepository.save(paymentStatusCommand);
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
