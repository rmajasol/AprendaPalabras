/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import exceptions.LoginIncorrectException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author http://sanjaal.com/java/tag/simple-java-tutorial-password-encryption/
 */
public class Encripter {

    public static synchronized String encrypt(String plaintext,
            String algorithm, String encoding) throws Exception {
        MessageDigest msgDigest = null;
        String hashValue = null;
        try {
            msgDigest = MessageDigest.getInstance(algorithm);
            msgDigest.update(plaintext.getBytes(encoding));
            byte rawByte[] = msgDigest.digest();
            hashValue = (new BASE64Encoder()).encode(rawByte);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exists");
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding Is Not Supported");
        }
        return hashValue;
    }
    
    /**
     * @param password contraseña introducida por el usuario.
     * @return contraseña encriptada en sha1 previamente convertida a minúsculas.
     * @throws Exception 
     */
    public static synchronized String encPassword(String password) throws Exception {
        String passwordEnc;
        try {
            passwordEnc = Encripter.encrypt(password.toLowerCase(),
                    "SHA-1", "UTF-8");
        } catch (Exception ex) {
            throw new Exception("Error al codificar la contraseña: "
                    + ex.getMessage());
        }
        return passwordEnc;
    }
}
