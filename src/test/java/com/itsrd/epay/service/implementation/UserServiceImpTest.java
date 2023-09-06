package com.itsrd.epay.service.implementation;

import com.itsrd.epay.repository.AddressRepository;
import com.itsrd.epay.repository.UserRepository;
import com.itsrd.epay.repository.WalletRepository;
import com.itsrd.epay.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private WalletRepository walletRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImp(userRepository, addressRepository, walletRepository);
    }

    //        check for some faild response


    @Test
    void saveUser() {

//        UserRequest userRequest = new UserRequest("Rudresh", "Gupta", "896084437", "R@gmil.com", "male", "sdeoria", "deoria", "up", "274001");
//        User actualUser = userService.saveUser(userRequest);
//        assertThat(actualUser).isNotNull();
    }


    @Test
    void getUser() {
//        User user = new User(1L, "Ankit", "Gupta", "8960864437", "rudresh@gmail.com", "male", 1L, 1L);
//
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        User userReturn = userService.getUser(1L);
//
//        user.setFirstName("Rudresh");
//        assertThat(userReturn).isEqualTo(user);
//        assertThat(userReturn).isNotNull();

    }

    @Test
    void updateUser() {

    }
}