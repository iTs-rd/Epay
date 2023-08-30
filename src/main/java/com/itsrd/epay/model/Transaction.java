package com.itsrd.epay.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long remitterUserId;

    @NotNull
    private String type;

    @NotNull
    private String description;

    private String remark;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Transaction(Long remitterUserId, String type, String description, String remark) {
        this.remitterUserId = remitterUserId;
        this.type = type;
        this.description = description;
        this.remark = remark;
    }
}
