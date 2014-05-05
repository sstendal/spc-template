package spctemplate;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Sample rest service
 */
@Path("/msg")
public class MsgRestService {

    /**
     * Call that does not require authenticated users
     *
     * Test:
     * curl http://localhost:8080/msg/free
     */
    @GET
    @Produces("text/plain")
    @Path("/free")
    public Response getFree() {
        return Response.status(200).entity("Free for all msg").build();
    }

    /**
     * Call that only accepts authenticated users
     *
     * Test with basic authentication
     * curl -u johndoe:johndoe http://localhost:8080/msg/restricted
     * <p/>
     * Test with JWT session token
     * curl -H "Authorization: Bearer eyJ...oPw" http://localhost:8080/msg/restricted
     */
    @GET
    @Produces("text/plain")
    @Path("/restricted")
    @RolesAllowed("user")
    public Response getRestricted(@Context SecurityContext sc) {
        String msg = "Restricted msg. Only for your eyes, " + sc.getUserPrincipal().getName();
        return Response.status(200).entity(msg).build();
    }

    /**
     * Call that always will be rejected
     *
     * Test:
     * curl -u johndoe:johndoe http://localhost:8080/msg/secret
     */
    @GET
    @Produces("text/plain")
    @DenyAll
    @Path("/secret")
    public Response getSecret() {
        return Response.status(200).entity("Secret msg").build();
    }

}