package com.itsrd.epay.controller;

import com.itsrd.epay.model.Transaction;
import com.itsrd.epay.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statement")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user")
    public ResponseEntity<Iterable<Transaction>> getByUser(@RequestParam Long id, @RequestParam Integer pageNumber) {
        return new ResponseEntity<>(transactionService.getStatement(id, pageNumber), HttpStatus.OK);
    }
}
