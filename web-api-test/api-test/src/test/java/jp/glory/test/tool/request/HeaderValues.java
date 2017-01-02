package jp.glory.test.tool.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HeaderValues {

    private Optional<CsrfToken> token = Optional.empty();

    public void setToken(final CsrfToken token) {

        this.token = Optional.ofNullable(token);
    }

    public Map<String, String> toMap() {

        final Map<String, String> returnMap = new HashMap<>();

        token.ifPresent(v -> returnMap.put("X-CSRF-TOKEN", v.getValue()));

        return Collections.unmodifiableMap(returnMap);
    }
}

