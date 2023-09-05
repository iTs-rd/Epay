package com.itsrd.epay.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "otps")
@NoArgsConstructor
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNo;

    private String Otp;

    public Otp(String phoneNo, String Otp) {
        this.phoneNo = phoneNo;
        this.Otp = Otp;
    }

}
