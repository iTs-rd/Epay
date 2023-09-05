package com.itsrd.epay.model;


import com.itsrd.epay.dto.UserRequest;
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

//    ignore
    private String password;

    //    ignore
    private boolean isActive;

    //    ignore
    private String roles;

    private Long address_id;

    private Long wallet_id;

    public User(UserRequest userRequest) {
        this.firstName = userRequest.getFirstName();
        this.lastName = userRequest.getLastName();
        this.gender = userRequest.getGender();
        this.email = userRequest.getEmail();
        this.phoneNo = userRequest.getPhoneNo();
        this.password=userRequest.getPassword();

        this.roles="ROLE_ADMIN";
        this.isActive=false;
    }

}
