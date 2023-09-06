package com.itsrd.epay.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferMoneyRequest {

    @NotNull
    @Min(value = 1)
    private Double amount;

    private String remark;

    @NotNull
    @Size(min = 10, max = 10)
    private String beneficiaryPhoneNo;
}
