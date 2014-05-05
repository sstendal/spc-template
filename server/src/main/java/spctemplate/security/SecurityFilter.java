package spctemplate.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Servlet filter that registers a user principal in the http request for authenticated users.
 * <p/>
 * Accepts either a Basic Authentication with username and password or a valid JWT token.
 * <p/>
 * User: Sigurd Stendal
 * Date: 13.10.13
 */
public class SecurityFilter implements Filter {

    private static final String BASIC_AUTH_DOMAIN = "SPC Template";

    protected Logger logger = LoggerFactory.getLogger(SecurityFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        logger.debug("Checking Authorization header field");
        AuthHeader data = AuthHeaderParser.parseHeader(request.getHeader("Authorization"));
        switch (data.type) {
            case EMPTY:
                break;
            case BASIC:
                if (!DummyLoginService.checkPassword(data.username, data.password)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader("WWW-Authenticate", "Basic realm=" + BASIC_AUTH_DOMAIN);
                    return;
                }
                request = wrappedHttpServletRequest(request, data.username, null);
                break;
            case TOKEN:
                try {
                    JwtClaims claims = JsonWebToken.verifyAndGetClaims(data.token);
                    if (!DummyLoginService.checkSessionId(claims.getSessionId())) {
                        logger.error("Invalid session id: " + claims.getSessionId());
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    request = wrappedHttpServletRequest(request, claims.getSubject(), claims.getSessionId());
                } catch (Exception e) {
                    logger.error("System error while verifying JWT token: " + e.getMessage(), e);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }

                break;

            case UNKNOWN:

        }

        filterChain.doFilter(request, response);
    }

    private HttpServletRequest wrappedHttpServletRequest(HttpServletRequest request, String username, String sessionId) {

        final User user = new User(username, sessionId);

        return new HttpServletRequestWrapper(request) {
            @Override
            public Principal getUserPrincipal() {
                return user;
            }

            @Override
            public String getAuthType() {
                return HttpServletRequest.BASIC_AUTH;
            }

            @Override
            public String getRemoteUser() {
                return user.getName();
            }

            @Override
            public boolean isUserInRole(String role) {
                return role.equals("user");
            }

        };
    }

    @Override
    public void destroy() {
        // Nothing
    }

}
