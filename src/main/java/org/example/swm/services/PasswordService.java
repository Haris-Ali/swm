package org.example.swm.services;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordService {
    private static final String FILE_NAME = "users.dat";
    //<-***** https://dzone.com/articles/secure-password-hashing-in-java - START

    /**
     * Method to hash password with SHA-512 algorithm and salt
     * @param password the input password from user
     * @param salt the 16 byte salt
     * @return the SHA-512 hashed password
     * @throws NoSuchAlgorithmException exception for missing algorithm
     */
    public String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
    //<-***** https://dzone.com/articles/secure-password-hashing-in-java - END

    /**
     * Method to verify user credentials (username and password) from file
     * @param username username of user
     * @param password password of user
     * @return boolean to identify if user exists or not
     * @throws IOException exception for error in file read/write
     * @throws NoSuchAlgorithmException exception for missing algorithm
     */
    public boolean verifyUser(String username, String password) throws IOException, NoSuchAlgorithmException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username)) {
                    String storedHash = parts[1];
                    byte[] salt = Base64.getDecoder().decode(parts[2]);

                    String hashedInputPassword = hashPassword(password, salt);
                    return hashedInputPassword.equals(storedHash);
                }
            }
        }
        return false;
    }

    /**
     * Save user credentials (hashed password and salt) to file.
     *
     * @param username the username
     * @param password the password
     * @throws IOException              if file operations fail
     * @throws NoSuchAlgorithmException if hashing algorithm is missing
     */
    public void saveUser(String username, String password) throws IOException, NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        String hashedPassword = hashPassword(password, salt);
        String saltEncoded = Base64.getEncoder().encodeToString(salt);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + hashedPassword + "," + saltEncoded);
            writer.newLine();
        }
    }
}
