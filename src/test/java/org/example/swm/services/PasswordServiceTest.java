package org.example.swm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordServiceTest {
    MockPasswordService mockPasswordService;

    @BeforeEach
    void setUp() {
        mockPasswordService = new MockPasswordService();
    }

    /**
     * Test for a new user signup/save
     * @throws NoSuchAlgorithmException algorithm exception
     */
    @Test
    void saveVerifyUserSuccessTest() throws NoSuchAlgorithmException {
        String username = "testuser1";
        String password = "test123";
        mockPasswordService.saveUser(username, password);

        boolean isVerified = mockPasswordService.verifyUser(username, password);
        assertTrue(isVerified, "User verification should succeed with correct credentials");
    }


    /**
     * Test for a wrong user password
     * @throws NoSuchAlgorithmException algorithm exception
     */
    @Test
    void verifyUserWrongPasswordTest() throws NoSuchAlgorithmException {
        String username = "testuser1";
        String password = "test123";
        mockPasswordService.saveUser(username, password);

        boolean isVerified = mockPasswordService.verifyUser(username, "test1234");
        assertFalse(isVerified, "User verification should fail with incorrect password");
    }

    /**
     * Test for a wrong username
     * @throws NoSuchAlgorithmException algorithm exception
     */
    @Test
    void verifyUserWrongUsernameTest() throws NoSuchAlgorithmException {
        String username = "testuser1";
        String password = "test123";
        mockPasswordService.saveUser(username, password);

        boolean isVerified = mockPasswordService.verifyUser("test1", password);
        assertFalse(isVerified, "User verification should fail with incorrect username");
    }

}