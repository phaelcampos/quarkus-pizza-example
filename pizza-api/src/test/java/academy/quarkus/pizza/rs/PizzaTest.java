package academy.quarkus.pizza.rs;


import academy.quarkus.pizza.model.*;
import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

@QuarkusTest
public class PizzaTest {

    @Inject
    PizzaResource pizzas;

    @BeforeAll
    @Transactional
    public static void beforeAll(){
        var store = new Store();
        store.name = "Pizza shack";
        store.code = RandomStringUtils.randomAlphanumeric(10);
        store.persist();

    }


    /**
     * Initial pizza order flow;
     * 1. Show menu of the nearest store
     * 2. Add pizza to cart
     * 3. Review Cart before checkout
     * 4. Checkout
     * 5. Delivery
     * 6. Feedback
     */

    @Test
    public void testGetPizza(){
        List<Pizza> ps = (List<Pizza>) pizzas.getPizzas();
        assertFalse(ps.isEmpty());
    }

    @Test
    public void testFindNearestStore(){
        //GIVEN
        var location = Location.current();
        //WHEN
        var store = Store.findNearest(location);
        //THEN
        assertNotNull(store);
    }

    @Test
    public void testAddToTicket(){
        //GIVEN
        var store = Store.persist("Pizza shack", "__test__");

        var trad = Category.persist(store, "Tradicional", "10.99");
        var marg = Pizza.persist("margherta");
        var cala = Pizza.persist("Calabresa");
        trad.addPizzas(cala, marg);
        var raphael = Person.persist("Raphael", "teste@teste.com", "12230075");

        //WHEN
        var ticket = Ticket.persist(raphael, "Av teste", "test","12230075");
        ticket.addItem(marg, trad.price, 2);
        ticket.addItem(cala, trad.price, 1);
        var ticketValue = ticket.getValue();
        //THEN
        var expectedValue = new BigDecimal("32.97");
        assertEquals(expectedValue, ticketValue);
    }
}
