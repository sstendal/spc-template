package spctemplate.security;

/**
 * Data class for Json rendering
 * <p/>
 * User: Sigurd Stendal
 * Date: 28.04.14
 */
public class JsonToken {

    private String token;

    public JsonToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
