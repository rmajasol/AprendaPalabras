/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;

/**
 *
 * @author yomac
 */
public class RandomString {
    public static String genRandomString(int length) {
        String randomString = "";
        //toma los milisegundos a la hora de ejecutar la sentencia de abajo 
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        //y se lo pasa al objeto Random
        Random r = new Random(milis);
        for(int i = 0; i < length; i++) {
            //establece un rango aleatorio entre 0 y 64-1 y lo convierte a char
            char c = (char) r.nextInt(64);
            //si el caracter convertido está entre alguno de estos ent. se añade a la cadena
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                randomString += c;
            }
        }
        return randomString;
    }
}
