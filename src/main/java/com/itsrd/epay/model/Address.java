package com.itsrd.epay.model;


import com.itsrd.epay.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
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
