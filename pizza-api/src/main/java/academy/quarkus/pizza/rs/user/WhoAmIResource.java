package academy.quarkus.pizza.rs.user;

import io.quarkus.logging.Log;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/user/whoami")
public class WhoAmIResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getWhoAMi(){
        var name = "anonymous";
        if(! identity.isAnonymous()){
            name = identity.getPrincipal().getName();
        }
        Map<String, String> result = Map.of(
                "name",name
        );
        Log.infof("WhoAmI() = %s", result);
        return result;
    }
}
