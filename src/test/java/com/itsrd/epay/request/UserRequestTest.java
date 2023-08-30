package com.itsrd.epay.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserRequestTest {

    @Test
    public void userRequestTest() {
//        need to give error
        UserRequest userRequest=new UserRequest("","Gupta","896084437","R@gmil.com","male","sdeoria","deoria","up","274001");
        assertThat(userRequest).isNotNull();



    }

}