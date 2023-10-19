package com.itsrd.epay.dto.requests.walletRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyRequest {

    @NotNull
    @Min(value = 1)
    private Double amount;

    private String remark;
}
