package com.jee.crud.controller;

import com.jee.crud.model.Book;
import com.jee.crud.model.Command;
import com.jee.crud.model.Customer;
import com.jee.crud.model.PaymentStatusCommand;
import com.jee.crud.repository.BookRepository;
import com.jee.crud.repository.CommandRepository;
import com.jee.crud.repository.CustomerRepository;
import com.jee.crud.repository.PaymentStatusCommandRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/command")
public class CommandController {
    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private PaymentStatusCommandRepository paymentStatusCommandRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @GetMapping("/{command}")
    public List<Command> getCommandsByPaymentStatusCommandId(@PathVariable int paymentId) {
        PaymentStatusCommand paymentStatusCommand = paymentStatusCommandRepository.findById(paymentId).orElse(null);
        if (paymentStatusCommand != null) {
            return commandRepository.findByPaymentStatusCommand(paymentStatusCommand);
        } else {
            System.out.println("getCommandsByPaymentStatusCommandId vide ");
            return null;
        }
    }
    
    @DeleteMapping("/delete/{command}")
    public ResponseEntity<String> deleteCommand(@PathVariable int paymentId) {
    	PaymentStatusCommand paymentStatusCommand = paymentStatusCommandRepository.findById(paymentId).orElse(null);
    	if (paymentStatusCommand != null) {
    		List<Command> commands = commandRepository.findByPaymentStatusCommand(paymentStatusCommand);
    		for(Command command_to_delete: commands) {
    			commandRepository.delete(command_to_delete);
    		}
    		paymentStatusCommandRepository.delete(paymentStatusCommand);
    		return ResponseEntity.ok("Success");
    	}
    	else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    @PutMapping("/quantity/{commandId}")
    public ResponseEntity<String> updateQuantity(@RequestBody String commandJson, @PathVariable int commandId) {
        Command command = commandRepository.findById(commandId).orElse(null);
        
        // parsing file "JSONExample.json"
        Object obj;
        long quantity = 0;
		try {
			obj = new JSONParser().parse(commandJson);
			JSONObject jo = (JSONObject) obj;
			quantity = (long) jo.get("quantity");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.ok("JSON is not correctly set. 'quantity' data must be set with an integer value.");
		}
		
        if(command!=null) {
        	command.setQuantity((int)quantity);
        	commandRepository.save(command);
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<String> createCommand(@RequestBody String commandJson) {
        String pattern = "\"id\":\\s*(\\d+),\\s*\"paymentStatusCommand\":\\s*(\\d+),\\s*\"book\":\\s*(\\d+),\\s*\"quantity\":\\s*(\\d+),\\s*\"customer\":\\s*(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(commandJson);

        if (m.find()) {
            int id = Integer.parseInt((m.group(1)));
            int paymentStatusCommand = Integer.parseInt(m.group(2));
            String book = m.group(3);
            int quantity = Integer.parseInt(m.group(4));
            Long customer = Long.valueOf(m.group(5));

            PaymentStatusCommand paymentStatusCommandById = paymentStatusCommandRepository.findById(paymentStatusCommand).orElse(null);
            if (paymentStatusCommandById == null) {
                paymentStatusCommandById = new PaymentStatusCommand((long) paymentStatusCommand, false);
                paymentStatusCommandRepository.save(paymentStatusCommandById);
                paymentStatusCommandById = paymentStatusCommandRepository.findById(paymentStatusCommand).orElse(null);
            }

            Book bookById = bookRepository.findById(book).orElse(null);
            if (bookById == null) {
                String errorBookNotFound = String.format("No book_id found for id : %s", book);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBookNotFound);
            }

            Customer customerById = customerRepository.findById(customer).orElse(null);
            if (customerById == null) {
                String errorCustomerNotFound = String.format("No customer_id found for id : %s", customer);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorCustomerNotFound);
            }

            Command command = new Command(paymentStatusCommandById, bookById, quantity, customerById);
            commandRepository.save(command);

            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
