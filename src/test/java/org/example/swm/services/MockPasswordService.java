package org.example.swm.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

public class MockPasswordService extends PasswordService {
    private boolean isUserSaved = false;
    private boolean isUserVerified = false;

    private final HashMap<String, UserRecord> userStorage = new HashMap<>();

    // Inner class to store hashed password and salt
    private static class UserRecord {
        String hashedPassword;
        byte[] salt;

        UserRecord(String hashedPassword, byte[] salt) {
            this.hashedPassword = hashedPassword;
            this.salt = salt;
        }
    }

    public MockPasswordService() {}

    @Override
    public boolean verifyUser(String username, String password) throws NoSuchAlgorithmException {
        UserRecord record = userStorage.get(username);
        if (record == null) return false;
        String hashedInputPassword = hashPassword(password, record.salt);
        return hashedInputPassword.equals(record.hashedPassword);
    }

    @Override
    public void saveUser(String username, String password) throws NoSuchAlgorithmException {
        if (userStorage.containsKey(username)) {
            throw new IllegalArgumentException("User already exists");
        }

        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        String hashedPassword = hashPassword(password, salt);
        userStorage.put(username, new UserRecord(hashedPassword, salt));
    }
}
