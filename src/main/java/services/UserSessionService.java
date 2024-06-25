package services;
import DAOs.DataAccessException;
import DAOs.UserSessionDAO;
import models.UserSession;

public class UserSessionService {

    private final UserSessionDAO UserSessionDAO;

    public UserSessionService(UserSessionDAO UserSessionDAO) {
        this.UserSessionDAO = UserSessionDAO;
    }

    public UserSession createSession(long userId, long timeout) throws DataAccessException {
        return UserSessionDAO.create(userId, timeout);
    }

    public UserSession getSessionById(String sessionId) throws DataAccessException {
        return UserSessionDAO.getById(sessionId);
    }

    public void updateSessionTimeout(String sessionId, long newTimeout) throws DataAccessException {
        UserSessionDAO.updateTimeout(sessionId, newTimeout);
    }

    public void invalidateSession(String sessionId) throws DataAccessException {
        UserSessionDAO.delete(sessionId);
    }
}
