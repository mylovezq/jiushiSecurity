package com.jiushi.auth.util;//package com.jiushi.auth.util;
//
//import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.InvalidKeyException;
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Base64;
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//public class TotpUtil {
//
//    private static KeyGenerator keyGenerator;
//    private static  final  long TIME_STEP = 60 * 5L;
//
//    private static  final  int PASSWORD_LENGTH = 6;
//
//    private TimeBasedOneTimePasswordGenerator totp;
//
//    {
//        try {
//            totp = new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(TIME_STEP));
//            keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
//            //SHA-1 -256   64字节(512位)
//            //SHA512   128字节 1024位
//            keyGenerator.init(512);
//        } catch (NoSuchAlgorithmException e) {
//            log.error("没有算法",e);
//        }
//    }
//
//
//    public String createTotp(Key key, Instant time) throws InvalidKeyException {
//        int password = totp.generateOneTimePassword(key, time);
//        String format = "%0" + PASSWORD_LENGTH +"d";
//        return String.format(format,password);
//    }
//
//    public String createTotp(String key) throws InvalidKeyException {
//        return createTotp(decodeKeyFromString(key),Instant.now());
//    }
//
//
//    public boolean verifyTotp(Key key,String code) throws InvalidKeyException {
//        Instant now = Instant.now();
//        return code.equals(createTotp(key,now));
//    }
//    public boolean verifyTotp(String strKey,String code) throws InvalidKeyException {
//        return code.equals(createTotp(strKey));
//    }
//
//   private Key generateKey(){
//    return keyGenerator.generateKey();
//   }
//
//    private String encodeKeyToString(Key key){
//        return Base64.getEncoder().encodeToString(key.getEncoded());
//   }
//
//   public String encodeKeyToString(){
//        return encodeKeyToString(generateKey());
//   }
//
//   private Key decodeKeyFromString(String keyStr){
//        return new SecretKeySpec(Base64.getDecoder().decode(keyStr),totp.getAlgorithm());
//   }
//
//
//}
