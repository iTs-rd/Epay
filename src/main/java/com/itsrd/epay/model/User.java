package com.itsrd.epay.model;


import com.itsrd.epay.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

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

    private Long address_id;

    private Long wallet_id;

    public User(UserRequest userRequest) {
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.gender = userRequest.getGender();
        this.email = userRequest.getEmail();
        this.phoneNo = userRequest.getPhoneNo();
    }

}
