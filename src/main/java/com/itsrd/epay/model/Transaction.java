package com.itsrd.epay.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private String id;

    @NotNull
    private String remitterPhoneNo;

    @NotNull
    private String type;

    @NotNull
    private Double amount;

    @NotNull
    private String description;

    private String remark;

    private java.util.Date createdAt;

    public Transaction(String remitterPhoneNo, String type, Double amount, String description, String remark) {
        this.remitterPhoneNo = remitterPhoneNo;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.remark = remark;
        this.createdAt = new java.util.Date();
    }
}
