package kr.or.connect.reservation.config;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {
    static String encryptAlgorithm = "SHA-256";

    public String encode(String pwd) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance(encryptAlgorithm);
        messageDigest.update(pwd.getBytes());
        byte[] encode = messageDigest.digest();

        String encrypt = new BigInteger(encode).toString(10);
        return encrypt;
    }

    public boolean matches(String pwd, String encryptPwd) {
        String encrypt = null;
        try {
            encrypt = encode(pwd);
            if (encrypt.equals(encryptPwd)) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("존재하지 않는 암호화 암고리즘 입니다.");
        }

        return false;
    }
}
