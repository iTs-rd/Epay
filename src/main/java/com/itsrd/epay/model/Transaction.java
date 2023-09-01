package com.itsrd.epay.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "transaction")
public class Transaction {

    private String id;

    @NotNull
    private Long remitterUserId;

    @NotNull
    private String type;

    @NotNull
    private Double amount;

    @NotNull
    private String description;

    private String remark;

    private java.util.Date createdAt;

    public Transaction(Long remitterUserId, String type, Double amount, String description, String remark) {
        this.remitterUserId = remitterUserId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.remark = remark;
        this.createdAt = new java.util.Date();
    }
}
