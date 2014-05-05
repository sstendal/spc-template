package spctemplate.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**
 * Dummy service for authentication and session management. Replace with a real service.
 * <p/>
 * User: Sigurd Stendal
 * Date: 30.04.14
 */
public class DummyLoginService {

    private static Random random = new Random(System.currentTimeMillis());
    private static Collection<String> sessions = new LinkedList<>();


    public static boolean checkPassword(String username, String password) {
        return username != null && username.equals(password);
    }

    public static boolean checkSessionId(String sessionId) {
        return sessions.contains(sessionId);
    }

    public static String generateSessionId() {
        String sessionId = String.valueOf(random.nextLong());
        sessions.add(sessionId);
        return sessionId;
    }

    public static void invalidateSessionId(String sessionId) {
        sessions.remove(sessionId);
    }
}
