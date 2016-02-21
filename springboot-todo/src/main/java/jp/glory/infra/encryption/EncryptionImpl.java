package jp.glory.infra.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import jp.glory.domain.user.value.Password;

/**
 * 暗号化クラス.
 *
 * @author Junki Yamada
 */
@Component
public class EncryptionImpl implements Encryption {

    /**
     * {@inheritDoc}
     */
    @Override
    public Password encrypt(String value) {

        try {

            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            final byte[] encryptedValues = digest.digest(value.getBytes());
            final StringBuilder builder = new StringBuilder();
            for (final byte encryptedValue : encryptedValues) {

                builder.append(String.format("%02x", encryptedValue));
            }

            return new Password(builder.toString());
        } catch (final NoSuchAlgorithmException ex) {

            throw new RuntimeException(ex);
        }
    }

}