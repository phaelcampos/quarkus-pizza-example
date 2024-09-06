package academy.quarkus.pizza.rs;

import academy.quarkus.pizza.model.Category;
import academy.quarkus.pizza.model.Store;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/pizza")
public class PizzaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getPizzas() {
        var store = Store.findNearest();
        var categories = Category.listByStore(store);
        Map<String, Object> result = Map.of(
                "store", store,
                "categories", categories
        );
        return result;
    }
}
