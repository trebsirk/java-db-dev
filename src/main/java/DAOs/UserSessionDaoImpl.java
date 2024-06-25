package DAOs;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.UserSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class UserSessionDAOImpl implements UserSessionDAO {

    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public UserSessionDAOImpl(JedisPoolConfig jedisPoolConfig, String redisHostname, int redisPort, ObjectMapper objectMapper) {
        this.jedisPool = new JedisPool(jedisPoolConfig, redisHostname, redisPort);
        this.objectMapper = objectMapper;
    }

    @Override
    public UserSession getById(String sessionId) throws DataAccessException {
        try (Jedis jedis = jedisPool.getResource()) {
            String sessionJson = jedis.get(sessionId);
            if (sessionJson == null) {
                return null;
            }
            try {
                return objectMapper.readValue(sessionJson, UserSession.class);
            } catch (JsonProcessingException e) {
                throw new DataAccessException("Error parsing session data from Redis", e);
            }
        }
    }

    @Override
    public UserSession create(long userId, long timeout) throws DataAccessException {
        try (Jedis jedis = jedisPool.getResource()) {
            String sessionId = generateSessionId();
            UserSession session = new UserSession(sessionId, userId, timeout);
            String sessionJson;
            try {
                sessionJson = objectMapper.writeValueAsString(session);
            } catch (JsonProcessingException e) {
                throw new DataAccessException("Error serializing session data to JSON", e);
            }
            jedis.set(sessionId, sessionJson);
            jedis.expire(sessionId, (int) TimeUnit.MILLISECONDS.toSeconds(timeout));
            return session;
        }
    }

    @Override
    public void updateTimeout(String sessionId, long newTimeout) throws DataAccessException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.expire(sessionId, (int) TimeUnit.MILLISECONDS.toSeconds(newTimeout));
        }
    }

    @Override
    public void delete(String sessionId) throws DataAccessException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(sessionId);
        }
    }

    // Utility method to generate a unique session ID (implementation specific)
    private String generateSessionId() {
        // Replace with your preferred unique ID generation logic (e.g., UUID)
        return UUID.randomUUID().toString();
    }
}
