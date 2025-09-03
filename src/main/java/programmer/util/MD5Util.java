package programmer.util;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMd5(String input) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");// Static getInstance method is called with hashing MD5

            byte[] messageDigest = md.digest(input.getBytes()); // digest() method is called to calculate message digest
                                                                 //  of an input digest() return array of byte
            BigInteger no = new BigInteger(1, messageDigest);            // Convert byte array into signum representation
            String hashtext = no.toString(16);            // Convert message digest into hex value

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
