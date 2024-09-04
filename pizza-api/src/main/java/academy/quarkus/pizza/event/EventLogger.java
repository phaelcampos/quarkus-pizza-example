package academy.quarkus.pizza.event;

import io.quarkus.logging.Log;
import jakarta.enterprise.event.Observes;

public class EventLogger {
    public void onTicketSubmitted(@Observes TicketSubmitted ev){
        Log.infof("Ticket submitted [%s] at [%s]", ev.ticket().id, ev.submittedAt());
    }
}
