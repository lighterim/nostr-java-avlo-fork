package nostr.base;

import nostr.crypto.bech32.Bech32Prefix;
import nostr.crypto.schnorr.Schnorr;
import nostr.util.NostrUtil;

import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.ECNamedCurveTable;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.Security;

/**
 *
 * @author squirrel
 */
public class PrivateKey extends BaseKey {

    public PrivateKey(byte[] rawData) {
        super(KeyType.PRIVATE, rawData, Bech32Prefix.NSEC);
    }

    public PrivateKey(String hexPrivKey) {
    	super(KeyType.PRIVATE, NostrUtil.hexToBytes(hexPrivKey), Bech32Prefix.NSEC);
    }
    
    /**
     * 
     * @return A strong pseudo random private key 
     */
    public static PrivateKey generateRandomPrivKey() {
    	return new PrivateKey(Schnorr.generatePrivateKey());
    }

    /**
     * 将原始私钥数据转换为 Java PrivateKey 对象，供加密/解密操作使用
     *
     * @return Java PrivateKey 对象
     * @throws Exception 如果转换失败
     */
    public java.security.PrivateKey toJavaPrivateKey() throws Exception {
        BigInteger privateKeyInt = new BigInteger(1, this.getRawData());
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(privateKeyInt, ECNamedCurveTable.getParameterSpec("secp256k1"));
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        return keyFactory.generatePrivate(ecPrivateKeySpec);
    }
}
