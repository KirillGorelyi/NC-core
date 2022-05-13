package org.nc.core.config.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class PasswordEncryptor {
    private static MessageDigest messageDigest = null;
    private final BCryptPasswordEncoder encoder;


    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PasswordEncryptor(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public String encryptPassword(String password){
        messageDigest.update(password.getBytes());
        return encoder.encode(password);
    }

    public boolean match(String unknownPassword, String existedPassword){
        return encoder.matches(unknownPassword, existedPassword);
    }
}
