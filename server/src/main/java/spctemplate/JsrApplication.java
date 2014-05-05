package spctemplate;

import org.glassfish.jersey.server.ResourceConfig;
import spctemplate.security.RolesAllowedDynamicFeature;

/**
 * The JSR application. Rename to something that matches your project. Remember to rename the configuration entry
 * in web xml as well (javax.ws.rs.Application).
 * <p/>
 * User: Sigurd Stendal
 * Date: 28.02.14
 */
public class JsrApplication extends ResourceConfig {


    public JsrApplication() {
        super();
        register(RolesAllowedDynamicFeature.class);
    }
}
