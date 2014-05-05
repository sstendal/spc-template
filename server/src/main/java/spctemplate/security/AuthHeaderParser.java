package spctemplate.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * Provides functions for parsing the Authentication Header.
 * <p/>
 * User: Sigurd Stendal
 * Date: 11.10.12
 */
public class AuthHeaderParser {

    private static final String BASIC = "Basic ";
    private static final String BEARER = "Bearer ";
    protected static Logger logger = LoggerFactory.getLogger(AuthHeaderParser.class);

    public static AuthHeader parseHeader(String header) {
        AuthHeader h = new AuthHeader(prefix2Type(header));
        switch (h.type) {
            case BASIC:
                parseBasic(h, header);
                break;
            case TOKEN:
                parseJwt(h, header);
                break;
        }
        return h;

    }

    private static AuthHeader.Type prefix2Type(String prefix) {
        if (prefix == null) return AuthHeader.Type.EMPTY;
        if (prefix.startsWith(BASIC)) return AuthHeader.Type.BASIC;
        if (prefix.startsWith(BEARER)) return AuthHeader.Type.TOKEN;
        return AuthHeader.Type.UNKNOWN;
    }

    private static void parseBasic(AuthHeader h, String header) {
        if (header.length() == BASIC.length()) {
            logger.error("Failed while parsing Authorization header field. Authorization header was only '" + BASIC + "'. Username and password was missing.");
            h.type = AuthHeader.Type.UNKNOWN;
            return;
        }
        String authorization = new String(Base64.getDecoder().decode(header.substring(BASIC.length())));
        int i = authorization.indexOf(":");
        if (i == -1) {
            logger.error("Failed while parsing Authorization header field. Base64 encoded data did not contain any ':'");
            h.type = AuthHeader.Type.UNKNOWN;
            return;
        }
        h.username = authorization.substring(0, i);
        h.password = authorization.substring(h.username.length() + 1);
    }

    private static void parseJwt(AuthHeader h, String header) {
        if (header.length() == BEARER.length()) {
            logger.error("Failed while parsing Authorization header field. Authorization header was only '" + BEARER + "'. Token was missing.");
            h.type = AuthHeader.Type.UNKNOWN;
            return;
        }
        h.token = header.substring(BEARER.length());
    }
}

/**
 * A parsed Authentication header
 */
class AuthHeader {

    enum Type {EMPTY, BASIC, TOKEN, UNKNOWN}

    AuthHeader(Type type) {
        this.type = type;
    }

    Type type;
    String username;
    String password;
    String token;
}

