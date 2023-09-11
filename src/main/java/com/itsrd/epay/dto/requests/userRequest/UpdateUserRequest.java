package com.itsrd.epay.dto.requests.userRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private final String firstNameMessage = "First Name Should be between 1 to 256 characters";
    private final String lastNameMessage = "Last Name Should be between 1 to 256 characters";
    private final String phoneNoMessage = "Phone no Should be of 10 digit";
    private final String zipCodeMessage = "Zip Code Should be of 6 digit";


    @NotNull(message = firstNameMessage)
    @Size(min = 2, max = 256, message = firstNameMessage)
    private String firstName;

    @NotNull(message = lastNameMessage)
    @Size(min = 1, max = 256, message = lastNameMessage)
    private String lastName;

    @NotNull(message = phoneNoMessage)
    @Size(min = 10, max = 10, message = phoneNoMessage)
    private String phoneNo;

    @Size(min = 4, max = 256)
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String gender;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull(message = zipCodeMessage)
    @Size(min = 6, max = 6, message = zipCodeMessage)
    private String zipCode;

}
