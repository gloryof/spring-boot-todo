package jp.glory.api.todos.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TodoPutRequest {

    private static final String SUMMARY = "summary";
    private static final String MEMO = "memo";
    private static final String COMPLETED = "completed";
    private static final String VERSION = "version";

    private final int id;
    private String summary = null;
    private String memo = null;
    private boolean completed = false;
    private final int version;

    public TodoPutRequest(final int id, final int version) {

        this.id = id;
        this.version = version; 
    }


    public int getId() {
        return id;
    }
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
        returnMap.put(VERSION, version);

        return Collections.unmodifiableMap(returnMap);
    }


    public int getVersion() {
        return version;
    }
}
