package com.itsrd.epay.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    public Wallet() {
        this.amount = 0.0;
    }

    public Wallet(Double amount) {
        this.amount = amount;
    }

    public Wallet(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }
}
