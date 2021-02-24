package com.nsn.telkomsel.mics.util;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MICSPasswordEnc {
	
	public String getEncryptedPassword(String password) throws NoSuchAlgorithmException,InvalidKeySpecException{
		if (password == null || password.equals("")) return null;
		
		String algo = "PBKDF2WithHmacSHA1";
		int derKeyLength = 160;
		int iterations = 20000;
		KeySpec kSpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations,160);
		SecretKeyFactory skfEnc = SecretKeyFactory.getInstance(algo);
		return toHex(skfEnc.generateSecret(kSpec).getEncoded());
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
	
	private String salt = "8df89406ca8b3938";

}
