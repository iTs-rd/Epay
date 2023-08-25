package com.itsrd.epay.model;


import com.itsrd.epay.request.UserRequest;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String state;

    private String zipCode;

    public Address(UserRequest userRequest) {
        this.street = userRequest.getStreet();
        this.city = userRequest.getCity();
        this.state = userRequest.getState();
        this.zipCode = userRequest.getZipCode();
    }
}
