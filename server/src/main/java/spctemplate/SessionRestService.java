package spctemplate;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spctemplate.security.DummyLoginService;
import spctemplate.security.JsonToken;
import spctemplate.security.JsonWebToken;
import spctemplate.security.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;

/**
 * Provides REST services for creating and invalidating a login session. Creates a JWT token for the session.
 * <p/>
 * User: Sigurd Stendal
 * Date: 30.04.14
 */
@Path("/session")
public class SessionRestService {

    private static Logger logger = LoggerFactory.getLogger(SessionRestService.class);

    protected ObjectMapper jsonMapper;

    public SessionRestService() {

        jsonMapper = new ObjectMapper();
    }

    /**
     * Creates a login session. Returns a JWT token to the client.
     * <p/>
     * Test:
     * curl -H "Accept: application/json" --data "username=johndoe&password=johndoe" http://localhost:8080/session/login
     *
     * @return A JWT token
     */
    @POST
    @Produces("application/json")
    @Path("/login")
    @PermitAll
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) throws IOException
    {

        if (!DummyLoginService.checkPassword(username, password)) {
            logger.error("Login failed");
            return Response.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        } else {
            logger.info("Login successfull");
            String token = JsonWebToken.generate(username, DummyLoginService.generateSessionId());
            logger.debug("JWT: " + token);
            String result = jsonMapper.writeValueAsString(new JsonToken(token));
            return Response.status(200).entity(result).build();
        }

    }

    /**
     * Invalidates a login session. The corresponding JWT token will not be accepted after this.
     * <p/>
     * Test:
     * curl --data "" -H "Accept: application/json" -H "Authorization: Bearer eyJh...uY" http://localhost:8080/session/logout
     */
    @POST
    @Produces("application/json")
    @Path("/logout")
    @RolesAllowed("user")
    public Response logout(@Context SecurityContext sc) throws IOException {
        User user = (User) sc.getUserPrincipal();
        DummyLoginService.invalidateSessionId(user.getSessionId());

        return Response.status(200).build();
    }


}