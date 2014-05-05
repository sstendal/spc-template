package spctemplate.security;

import java.security.Principal;

/**
 * Implementation of Principal with an optional session id.
 * <p/>
 * User: Sigurd Stendal
 * Date: 28.02.14
 */
public class User implements Principal {

    private String username;
    private String sessionId;

    public User(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
