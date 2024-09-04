package academy.quarkus.pizza.rs;

import academy.quarkus.pizza.model.Delivery;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.*;

import jakarta.inject.Inject;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DeliveryResourceTest {

    @Inject
    Delivery delivery;

    @Test
    public void testUpdateLocation(){
        Log.infof("Testng %s", delivery.id);
        //send location update
        Double lat= -23.2424242, lon = -31.1231231;
        given()
                .pathParam("deliveryId", delivery.id)
                .body(Map.of(
                        "lat", lat,
                        "lon", lon
                ))
                .contentType("application/json")
                .when()
                    .post("/delivery/{deliveryId}/updateLocation")
                .then()
                .statusCode(200);
        //Check if location updated
        given()
                .pathParam("deliveryId", delivery.id)
                .contentType("application/json")
                .when()
                .get("/delivery/{deliveryId}")
                .then()
                .statusCode(200)
                .body("currentLocation.lat",closeTo(lat,0.0))
                .body("currentLocation.lon",closeTo(lon,0.0));
    }
}