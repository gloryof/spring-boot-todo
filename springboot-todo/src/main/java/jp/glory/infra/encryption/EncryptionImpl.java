package jp.glory.infra.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (value == null) {

            return new Password("");
        }

        return new Password(encoder.encode(value));
    }

}