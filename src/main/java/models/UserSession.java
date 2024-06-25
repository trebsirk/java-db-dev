package models;

import java.util.HashMap;
import java.util.Map;

public record UserSession(
        String sessionId,
        long userId,
        long lastAccessTime,
        Map<String, Object> data) {

    public UserSession(String sessionId, long userId, long timeout) {
        this(sessionId, userId, System.currentTimeMillis(), new HashMap<>());
    }

    public boolean isExpired(long ttl) {
        return (System.currentTimeMillis() - lastAccessTime) > ttl;
    }
    /*
    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void putData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }

   
    */
}
