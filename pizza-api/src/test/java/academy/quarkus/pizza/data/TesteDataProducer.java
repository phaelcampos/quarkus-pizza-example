package academy.quarkus.pizza.data;

import academy.quarkus.pizza.model.*;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TesteDataProducer {
    public Delivery delivery;

    public void init(@Observes StartupEvent ev){
        Store store = Store.persist("Testing store", "__TEST__");

        Category trad = Category.persist(store, "Tradicional", "10.99");
        Pizza marg = Pizza.persist("Marguerita");
        Pizza mush = Pizza.persist("Mushrooms");
        trad.addPizzas(marg,mush);

        Person person = Person.persist("Winona Courir", "teste@teste.com","21321321");
        Courier courier = Courier.persist(person.id, "123213213412");

        Ticket ticket = Ticket.persist(person, "Rabbit hole 1", "Tea room", "1231231");
        delivery = Delivery.persist(ticket.id, store.id, courier.id);
    }

    @Produces
    public Delivery createDelivery(){

        return delivery;
    }
}
