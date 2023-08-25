package com.itsrd.epay.model;


import com.itsrd.epay.request.UserRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNo;

    private String email;

    private String gender;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public User(UserRequest userRequest) {
        this.firstName= userRequest.getFirstName();
        this.lastName= userRequest.getLastName();
        this.gender= userRequest.getGender();
        this.email=userRequest.getEmail();
        this.phoneNo=userRequest.getPhoneNo();
    }
}
