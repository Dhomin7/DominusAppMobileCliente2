package DominusApp.view.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;

public class Hash {

    public static String encriptar(String texto, String algoritmo) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {

        MessageDigest message = MessageDigest.getInstance(algoritmo);

        //Retira acentos
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");

        byte messageDigest[] = message.digest(texto.getBytes("UTF-8"));

        // Colocar no formato hexadecimal
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        String senhahex = hexString.toString();

        return senhahex;
    }
}
