package nostr.id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.java.Log;
import nostr.base.ISignable;
import nostr.base.PrivateKey;
import nostr.base.PublicKey;
import nostr.base.Signature;
import nostr.crypto.schnorr.Schnorr;
import nostr.event.impl.GenericEvent;
import nostr.event.tag.DelegationTag;
import nostr.util.NostrUtil;

import javax.crypto.Cipher;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * @author squirrel
 */
@EqualsAndHashCode
@Data
@Log
public class Identity {

    @ToString.Exclude
    private final PrivateKey privateKey;

    private Identity(@NonNull PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Deprecated(forRemoval = true)
    public static Identity getInstance(@NonNull PrivateKey privateKey) {
        return new Identity(privateKey);
    }

    public static Identity create(@NonNull PrivateKey privateKey) {
        return new Identity(privateKey);
    }

    @Deprecated(forRemoval = true)
    public static Identity getInstance(@NonNull String privateKey) {
        return new Identity(new PrivateKey(privateKey));
    }

    public static Identity create(@NonNull String privateKey) {
        return new Identity(new PrivateKey(privateKey));
    }

    /**
     * @return A strong pseudo random identity
     */
    public static Identity generateRandomIdentity() {
        return new Identity(PrivateKey.generateRandomPrivKey());
    }

    public PublicKey getPublicKey() {
        try {
            return new PublicKey(Schnorr.genPubKey(this.getPrivateKey().getRawData()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Signature sign(@NonNull ISignable signable)  {
        if (signable instanceof GenericEvent genericEvent) {
            try {
                return signEvent(genericEvent);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (signable instanceof DelegationTag delegationTag) {
            try {
                return signDelegationTag(delegationTag);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException();
    }

    private Signature signEvent(@NonNull GenericEvent event) throws Exception {
        event.update();
        log.log(Level.FINER, "Serialized event: {0}", new String(event.get_serializedEvent()));
        final var signedHashedSerializedEvent = Schnorr.sign(NostrUtil.sha256(event.get_serializedEvent()), this.getPrivateKey().getRawData(), generateAuxRand());
        final Signature signature = new Signature();
        signature.setRawData(signedHashedSerializedEvent);
        signature.setPubKey(getPublicKey());
        event.setSignature(signature);
        return signature;
    }

    private Signature signDelegationTag(@NonNull DelegationTag delegationTag) throws Exception {
        final var signedHashedToken = Schnorr.sign(NostrUtil.sha256(delegationTag.getToken().getBytes(StandardCharsets.UTF_8)), this.getPrivateKey().getRawData(), generateAuxRand());
        final Signature signature = new Signature();
        signature.setRawData(signedHashedToken);
        signature.setPubKey(getPublicKey());
        delegationTag.setSignature(signature);
        return signature;
    }

    private byte[] generateAuxRand() {
        return NostrUtil.createRandomByteArray(32);
    }

    // 使用公钥加密
    public static String encryptWithPublicKey(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey.toJavaPublicKey());  // 使用公钥加密
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 使用私钥解密
    public String decryptWithPrivateKey(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("ECIES", "BC");
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey.toJavaPrivateKey());  // 使用私钥解密
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


    // 示例：使用公钥加密、私钥解密
    public static void main(String[] args) throws Exception {
        // 生成随机身份
        Identity identity = Identity.generateRandomIdentity();

        // 获取公钥和私钥
        PublicKey publicKey = identity.getPublicKey();
        System.out.println(publicKey.toString());
        PrivateKey privateKey = identity.getPrivateKey();

        // 加密消息
        String message = privateKey.toString();
        System.out.println("plain text: "+message);
        String encryptedMessage = Identity.encryptWithPublicKey(message, publicKey);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // 解密消息
        String decryptedMessage = identity.decryptWithPrivateKey(encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}