package models;

import java.util.HashMap;
import java.util.Map;

public class UserSession {

    private final String sessionId;
    private final long userId;
    private long lastAccessTime;
    private final Map<String, Object> data;
  
    public UserSession(String sessionId, long userId, long timeout) {
      this.sessionId = sessionId;
      this.userId = userId;
      this.lastAccessTime = System.currentTimeMillis();
      this.data = new HashMap<>();
    }
  
    // Getters and Setters
    public String getSessionId() {
      return sessionId;
    }
  
    public long getUserId() {
      return userId;
    }
  
    public void setLastAccessTime(long lastAccessTime) {
      this.lastAccessTime = lastAccessTime;
    }
  
    public long getLastAccessTime() {
      return lastAccessTime;
    }
  
    public Map<String, Object> getData() {
      return data;
    }
  
    // Method to add data to the session
    public void putData(String key, Object value) {
      data.put(key, value);
    }
  
    // Method to retrieve data from the session
    public Object getData(String key) {
      return data.get(key);
    }
  
    // Method to check if the session is expired based on a provided TTL (in milliseconds)
    public boolean isExpired(long ttl) {
      return (System.currentTimeMillis() - lastAccessTime) > ttl;
    }
  }
  