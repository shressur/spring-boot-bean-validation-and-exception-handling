package com.example.springbootexceptionhandling.api;

import com.example.springbootexceptionhandling.entity.Customer;
import com.example.springbootexceptionhandling.exception.CustomerNotFoundException;
import com.example.springbootexceptionhandling.exception.EmailAlreadyExistsException;
import com.example.springbootexceptionhandling.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerApi {
    private final CustomerService customerService;

    //Create: 201-CREATED
    @PostMapping("/new")
    public ResponseEntity<Customer> createNewCustomer(@Valid @RequestBody Customer newCustomer) throws EmailAlreadyExistsException {
        customerService.saveCustomer(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    //Read one: 200-OK
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> fetchSingleCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getSingleCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    //Read all: 200-OK
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> fetchAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    //Update: 202-ACCEPTED or 200-OK
    @PutMapping("/update")   //updates or creates record
    public ResponseEntity<Customer> updateExistingCustomer(@RequestBody @Valid Customer newCustomer) throws EmailAlreadyExistsException {
        return ResponseEntity.accepted().body(customerService.updateCustomer(newCustomer));
    }

    //Delete: 204-NO CONTENT or 200-OK
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException{
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Customer with id " + customerId + " deleted!");
    }


}

