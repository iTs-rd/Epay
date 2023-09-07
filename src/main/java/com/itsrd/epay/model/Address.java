package com.itsrd.epay.model;


import com.itsrd.epay.dto.requests.userRequest.CreateUserRequest;
import com.itsrd.epay.dto.requests.userRequest.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Address(CreateUserRequest createUserRequest) {
        this.street = createUserRequest.getStreet();
        this.city = createUserRequest.getCity();
        this.state = createUserRequest.getState();
        this.zipCode = createUserRequest.getZipCode();
    }

    public Address(UpdateUserRequest updateUserRequest) {
        this.street = updateUserRequest.getStreet();
        this.city = updateUserRequest.getCity();
        this.state = updateUserRequest.getState();
        this.zipCode = updateUserRequest.getZipCode();
    }
}
