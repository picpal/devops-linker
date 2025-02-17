package com.picpal.framework.common.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JasyptConfigTest {
    @Test
    void stringEncryptor() {
//        String username = "000";
//        String password = "000";

        String enckey = "dmVyeV92ZXJ5X2ltcG9ydGFudF9rZXk=";

//        log.info("####### jasypt ENC username : {} ",jasyptEncoding(username));
//        log.info("####### jasypt ENC password : {} ",jasyptEncoding(password));
        log.info("####### jasypt ENC password : {} ",jasyptEncoding(enckey));
    }

    public String jasyptEncoding(String value) {
        String key = "000"; // 암호화 키
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        pbeEnc.setIvGenerator(new RandomIvGenerator());
        return pbeEnc.encrypt(value);
    }
}