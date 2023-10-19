package com.itsrd.epay.dto.requests.walletRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {

    @NotNull
    @Min(value = 1)
    private Double amount;

    private String remark;

    @NotNull
    @Size(min = 10, max = 10)
    private String beneficiaryPhoneNo;
}
