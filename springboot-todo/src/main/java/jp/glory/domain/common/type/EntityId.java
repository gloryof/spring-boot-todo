package jp.glory.domain.common.type;

import java.util.Optional;
import lombok.Getter;

/**
 * エンティティのID
 * @author Junki Yamada
 */
public class EntityId {
    
    /**
     * 値.
     */
    @Getter
    private final Long value;

    /** 値が設定されているかのフラグ */
    @Getter
    private final boolean setValue;

    /**
     * 値を設定する
     * @param paramValue 値 
     */
    protected EntityId(final Long paramValue) {

        final Optional<Long> optionalValue = Optional.ofNullable(paramValue);
        value = optionalValue.orElse(0L);
        setValue = optionalValue.isPresent();
    }

    /**
     * IDが同じか比較する.
     * 
     * @param paramValue 比較対象ID
     * @return 一致している場合：true、一致していない場合：false
     */
    public boolean isSame(final EntityId paramValue) {

        final Optional<EntityId> optionalValue = Optional.ofNullable(paramValue);
        if (!optionalValue.isPresent()) {

            return false;
        }

        if (setValue != paramValue.isSetValue()) {

            return false;
        }

        return this.value.equals(optionalValue.get().value);
    }
}