package com.itsrd.epay.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
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


    public User(CreateUserRequest createUserRequest) {
        this.firstName = createUserRequest.getFirstName();
        this.lastName = createUserRequest.getLastName();
        this.gender = createUserRequest.getGender();
        this.email = createUserRequest.getEmail();
        this.phoneNo = createUserRequest.getPhoneNo();
        this.password = createUserRequest.getPassword();

        this.roles = "ROLE_USER";
        this.isActive = false;
    }

    public User(UpdateUserRequest updateUserRequest) {
        this.firstName = updateUserRequest.getFirstName();
        this.lastName = updateUserRequest.getLastName();
        this.gender = updateUserRequest.getGender();
        this.email = updateUserRequest.getEmail();
        this.phoneNo = updateUserRequest.getPhoneNo();
        this.password = updateUserRequest.getPassword();

        this.roles = "ROLE_USER";
        this.isActive = true;
    }
}
