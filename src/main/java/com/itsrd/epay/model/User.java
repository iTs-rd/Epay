package com.itsrd.epay.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsrd.epay.dto.UserRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNo;

    private String email;

    private String gender;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean isActive;

    @JsonIgnore
    private String roles;

    @JsonIgnore
    private Long address_id;

    @JsonIgnore
    private Long wallet_id;

    public User(UserRequest userRequest) {
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.gender = userRequest.getGender();
        this.email = userRequest.getEmail();
        this.phoneNo = userRequest.getPhoneNo();
        this.password = userRequest.getPassword();

        this.roles = "ROLE_USER";
        this.isActive = false;
    }

}
