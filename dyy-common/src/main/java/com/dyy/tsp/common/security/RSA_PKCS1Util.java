package com.dyy.tsp.common.security;

import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * @author yangyang
 */
@SuppressWarnings("all")
public class RSA_PKCS1Util {

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final Logger logger = LoggerFactory.getLogger(RSA_PKCS1Util.class);

    private static byte[] processBlock(AsymmetricBlockCipher eng, byte[] clearBytes, int maxBlock)
            throws InvalidCipherTextException, IOException {
        int inputLen = clearBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段处理
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxBlock) {
                cache = eng.processBlock(clearBytes, offSet, maxBlock);
            } else {
                cache = eng.processBlock(clearBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxBlock;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();

        return decryptedData;
    }

    /**
     * 用公钥处理
     * @param publicKey       公钥
     * @param clearBytes      待处理byte数组
     * @param forEncryption   true表示加密，false表示解密
     * @return
     * @throws Exception
     */
    public static byte[] processByPublicKey(RSAPublicKeyImpl publicKey, byte[] clearBytes, boolean forEncryption) throws Exception {
        BigInteger mod = publicKey.getModulus();
        BigInteger pubExp = publicKey.getPublicExponent();

        RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod,
                pubExp);

        AsymmetricBlockCipher eng = new RSAEngine();
        eng = new PKCS1Encoding(eng);
        eng.init(forEncryption, pubParameters);

        int maxBlock;
        if (forEncryption) {
            maxBlock = MAX_ENCRYPT_BLOCK;
        } else {
            maxBlock = MAX_DECRYPT_BLOCK;
        }

        return processBlock(eng, clearBytes, maxBlock);
    }

    /**
     * 用私钥处理
     * @param privateCrtKey   私钥
     * @param clearBytes      待处理byte数组
     * @param forEncryption   true表示加密，false表示解密
     * @return
     * @throws Exception
     */
    public static byte[] processByPrivateKey(RSAPrivateCrtKeyImpl privateCrtKey, byte[] clearBytes, boolean forEncryption) throws Exception {
        BigInteger mod = privateCrtKey.getModulus();
        BigInteger pubExp = privateCrtKey.getPublicExponent();
        BigInteger privExp = privateCrtKey.getPrivateExponent();
        BigInteger pExp = privateCrtKey.getPrimeExponentP();
        BigInteger qExp = privateCrtKey.getPrimeExponentQ();
        BigInteger p = privateCrtKey.getPrimeP();
        BigInteger q = privateCrtKey.getPrimeQ();
        BigInteger crtCoef = privateCrtKey.getCrtCoefficient();

        RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod,
                pubExp, privExp, p, q, pExp, qExp, crtCoef);

        AsymmetricBlockCipher eng = new RSAEngine();
        eng = new PKCS1Encoding(eng);
        eng.init(forEncryption, privParameters);

        int maxBlock;
        if (forEncryption) {
            maxBlock = MAX_ENCRYPT_BLOCK;
        } else {
            maxBlock = MAX_DECRYPT_BLOCK;
        }

        return processBlock(eng, clearBytes, maxBlock);
    }

    public static RSAPrivateCrtKeyImpl initPrivateKey(String privateKeyData) throws Exception {
        PEMParser reader = new PEMParser(new StringReader(privateKeyData));
        Object obj = reader.readObject();

        PEMKeyPair pair = (PEMKeyPair)obj;
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        RSAPrivateCrtKeyImpl privateKey = (RSAPrivateCrtKeyImpl)converter.getPrivateKey(pair.getPrivateKeyInfo());

        return privateKey;
    }

    public static RSAPublicKeyImpl initPublicKey(String pubilcKeyData) throws Exception {
        PEMParser reader = new PEMParser(new StringReader(pubilcKeyData));
        Object obj = reader.readObject();

        SubjectPublicKeyInfo publicKeyInfo = null;
        if (obj instanceof SubjectPublicKeyInfo) {
            publicKeyInfo = (SubjectPublicKeyInfo)obj;
        }

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        RSAPublicKeyImpl publicKey = (RSAPublicKeyImpl)converter.getPublicKey(publicKeyInfo);

        return publicKey;
    }
}
