package nostr.base;

import lombok.EqualsAndHashCode;
import nostr.crypto.bech32.Bech32Prefix;
import nostr.util.NostrUtil;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.security.KeyFactory;

/**
 *
 * @author squirrel
 */
@EqualsAndHashCode(callSuper = true)
public class PublicKey extends BaseKey {

    public PublicKey(byte[] rawData) {
        super(KeyType.PUBLIC, rawData, Bech32Prefix.NPUB);
    }

    public PublicKey(String hexPubKey) {
    	super(KeyType.PUBLIC, NostrUtil.hexToBytes(hexPubKey), Bech32Prefix.NPUB);
    }

    /**
     * 将原始公钥数据转换为 Java PublicKey 对象
     *
     * @return Java PublicKey 对象
     * @throws Exception 如果转换失败
     */
    public java.security.PublicKey toJavaPublicKey() throws Exception {
        // 打印公钥字节数组以进行调试
        byte[] rawKeyData = this.getRawData();
        System.out.println("Public Key Hex: " + NostrUtil.bytesToHex(rawKeyData));

        // 检查字节数组长度和前缀
        if (rawKeyData.length != 33 && rawKeyData.length != 65) {
            throw new IllegalArgumentException("Invalid public key length: " + rawKeyData.length);
        }
        if (rawKeyData[0] != 0x02 && rawKeyData[0] != 0x03 && rawKeyData[0] != 0x04) {
            throw new IllegalArgumentException("Invalid public key encoding format");
        }

        // 获取 secp256k1 曲线参数
        var curveParams = SECNamedCurves.getByName("secp256k1");

        // 解码原始公钥字节数组为椭圆曲线点
        var point = curveParams.getCurve().decodePoint(rawKeyData);

        // 创建 EC 参数规范
        ECParameterSpec ecSpec = new ECParameterSpec(
                curveParams.getCurve(),
                curveParams.getG(),
                curveParams.getN(),
                curveParams.getH(),
                curveParams.getSeed()
        );

        // 创建公钥规范
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(point, ecSpec);

        // 使用 Bouncy Castle 提供的 KeyFactory 生成公钥
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        return keyFactory.generatePublic(pubKeySpec);
    }
}
