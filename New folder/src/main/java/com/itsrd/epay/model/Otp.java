package com.itsrd.epay.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "otps")
@NoArgsConstructor
public class Otp {

    @Id
    private String phoneNo;

    private String Otp;

    public Otp(String phoneNo, String Otp) {
        this.phoneNo = phoneNo;
        this.Otp = Otp;
    }

}
