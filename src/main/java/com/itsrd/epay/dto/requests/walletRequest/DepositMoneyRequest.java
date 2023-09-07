package com.itsrd.epay.dto.requests.walletRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositMoneyRequest {

    @NotNull
    @Min(value = 1)
    private Double amount;

    private String remark;
}
