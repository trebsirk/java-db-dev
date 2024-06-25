package DAOs;

import models.UserSession;

public interface UserSessionDAO {

    // Get a UserSession object by its session ID
    UserSession getById(String sessionId) throws DataAccessException;
  
    // Create a new UserSession object for a user with a specified user ID and timeout
    UserSession create(long userId, long timeout) throws DataAccessException;
  
    // Update an existing UserSession object with a new timeout
    void updateTimeout(String sessionId, long newTimeout) throws DataAccessException;
  
    // Delete a UserSession object by its session ID
    void delete(String sessionId) throws DataAccessException;
  }
  