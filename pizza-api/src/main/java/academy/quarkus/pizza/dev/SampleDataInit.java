package academy.quarkus.pizza.dev;

import academy.quarkus.pizza.model.Category;
import academy.quarkus.pizza.model.Pizza;
import academy.quarkus.pizza.model.Store;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class SampleDataInit {
    @Inject
    LaunchMode mode;


    @Transactional
    public void init(@Observes StartupEvent ev){
        if(LaunchMode.NORMAL.equals(mode))
            return;
        var store = Store.persist("Pizza shack", "__default__");

        var trad = Category.persist(store, "Tradicional", "10.99");
        var marg = Pizza.persist("margherta");
        var cala = Pizza.persist("Calabresa");
        trad.addPizzas(cala, marg);

        var premium = Category.persist(store, "premium", "10.99");
        var cheeses = Pizza.persist("4 cheeses");
        var veggies = Pizza.persist( "Vegetables");
        var napoles = Pizza.persist("Napoletana");

        premium.addPizzas(cala, marg);
    }

}
