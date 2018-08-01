package com.mynotes.microservice.common.jwtresoureserver;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;


public class JWKTest {
    private static final String[] HEX_TABLE = new String[]{
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f",
        "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f",
        "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f",
        "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f",
        "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f",
        "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f",
        "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f",
        "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f",
        "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f",
        "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af",
        "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf",
        "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf",
        "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df",
        "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef",
        "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff",
    };

    public static String toHexFromBytes(byte[] bytes) {
        StringBuffer rc = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            rc.append(HEX_TABLE[0xFF & bytes[i]]);
        }
        return rc.toString();
    }

    // Build the public key from modulus and exponent
    public static PublicKey getPublicKey (String modulusB64u, String exponentB64u) throws NoSuchAlgorithmException, InvalidKeySpecException{
        //conversion to BigInteger. I have transformed to Hex because new BigDecimal(byte) does not work for me
        byte exponentB[] = Base64.getUrlDecoder().decode(exponentB64u);
        byte modulusB[] = Base64.getUrlDecoder().decode(modulusB64u);
        BigInteger exponent = new BigInteger(toHexFromBytes(exponentB), 16);
        BigInteger modulus = new BigInteger(toHexFromBytes(modulusB), 16);

        //Build the public key
        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey pub = factory.generatePublic(spec);

        return pub;
    }

    public final static void main (String argv[]) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, NoSuchProviderException, InvalidKeyException{
        String exponentB64u = "AQAB";
        String modulusB64u = "wWH7v9Oj7HZ5tqpjPtYVtQHdcNxCb-KuPZhmw0ymXP39xePiAOvYkZCj-OO2SzEBgWx44FriSpk9JlW6LHvkhtzU6UE4RMk9u2w-8MUMFLfXXl1LYXMGJ8uCm75-I-MupqVoM9XxLuG561d0OoGeIDGwp54FENNInNpcWqqx-gL0UzVYN3RaGVFAUd53eS118yOwtkCjjDUmWE7Gp4NGJDlB4o00ywSJmiwGFy-z_2v-oh4rtsNaqYsxe96MHHELZFbKKNQubWM5_th1D77jvT5cAUMssZ-b4cRqlHRCWCYWmoCoq2Xxf_bqO1buA3wFVppbR58uDHMpy34iXzI1Ew";
        String jwt = "eyJraWQiOiJ1TnFadFN1X0k1UTVDa05rTWtia2VkRmY5TWU0ckh6SHhqV0pSWDlic0pFIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiIwMHVmdDl6YTRtekVncW9HSDBoNyIsIm5hbWUiOiJEaGFuYW5qYXkgU2luZ2giLCJlbWFpbCI6ImRoYW5hbmpheTEyMDRAZ21haWwuY29tIiwidmVyIjoxLCJpc3MiOiJodHRwczovL2Rldi0yOTg1NTUub2t0YXByZXZpZXcuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiMG9hZnRlZjdlbWFUbWFUN0YwaDciLCJpYXQiOjE1MzMwNzUwNjIsImV4cCI6MTUzMzA3ODY2MiwianRpIjoiSUQuVXd0bnZJNDNqdVVZUmxMb21uaEtaRnJiQm9UZ181T3RZQnBCTk1naU9tcyIsImFtciI6WyJwd2QiXSwiaWRwIjoiMDBvZnQ5emExeDVUYVdmeFUwaDciLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJkaGFuYW5qYXkxMjA0QGdtYWlsLmNvbSIsImF1dGhfdGltZSI6MTUzMzA3NDc2MCwiYXRfaGFzaCI6InRsRFMwTV9TSi1Da1FLX0lFR2ZEQ1EiLCJsYXN0TmFtZSI6IlNpbmdoIiwiZmlyc3RuYW1lIjoiRGhhbmFuamF5Iiwicm9sZXMiOlsiU1lTQURNSU4iLCJFdmVyeW9uZSIsIkFwcHJvdmVycyJdLCJhcHBfdXNlcmlkIjoiZGhhbmFuamF5MTIwNEBnbWFpbC5jb20ifQ.OtAupvboIdmmaM5JXMCYMcjokhCLEJ57b54ylHiEzuQS8LSVhYWBVLYDfvihH9WKLB27EjRAzZ-ft4ZP9YjVP7XKo5h0k5RY_4xWrbyQtEucyPfhlGJn48dIs4O2uHeEUMMXTvBG4CNsl4_jaTlT8g67pWYS1t1MsH6sUY1TiFFwVPNbsZbVl9uYBLO0cBH6hm24KQ_4tavpI3l0o8vdfP9Itgs_zflbhc5IgVg2Vb9Pf-l2Zi_9lggqF9TpX2xz3WoONwvr7UQWn6wT3mw-Y-uCQTRMwZRsbRgRTZFVSu_g-_bzuzZMgAoaCCPv2aOSgUGqK7pYTI3AlS3ryLjj4g";

        //Build the public key from modulus and exponent
        PublicKey publicKey = getPublicKey (modulusB64u,exponentB64u);

        //print key as PEM (base64 and headers)
        String publicKeyPEM = 
              "-----BEGIN PUBLIC KEY-----\n" 
            + Base64.getEncoder().encodeToString(publicKey.getEncoded()) +"\n"
            + "-----END PUBLIC KEY-----";
        System.out.println(  publicKeyPEM);

        //get signed data and signature from JWT
        String signedData = jwt.substring(0, jwt.lastIndexOf("."));
        String signatureB64u = jwt.substring(jwt.lastIndexOf(".")+1,jwt.length());
        byte signature[] = Base64.getUrlDecoder().decode(signatureB64u);

        //verify Signature
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(signedData.getBytes());
        boolean v = sig.verify(signature);
        System.out.println(v);


    }

}
