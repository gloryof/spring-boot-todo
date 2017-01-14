package jp.glory.api.todos.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TodoPostRequest {

    private static final String SUMMARY = "summary";
    private static final String MEMO = "memo";
    private static final String COMPLETED = "completed";

    private String summary = null;
    private String memo = null;
    private boolean completed = false;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Map<String, Object> toMap() {

        final Map<String, Object> returnMap = new HashMap<>();

        returnMap.put(SUMMARY, summary);
        returnMap.put(MEMO, memo);
        returnMap.put(COMPLETED, Boolean.valueOf(completed));

        return Collections.unmodifiableMap(returnMap);
    }
}
