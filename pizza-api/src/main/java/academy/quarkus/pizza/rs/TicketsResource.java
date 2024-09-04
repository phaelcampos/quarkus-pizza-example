package academy.quarkus.pizza.rs;

import academy.quarkus.pizza.event.TicketSubmitted;
import academy.quarkus.pizza.model.Ticket;
import academy.quarkus.pizza.model.TicketStatus;
import com.github.benmanes.caffeine.cache.Ticker;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDateTime;
import java.util.Map;

@Path("/tickets")
@Transactional
public class TicketsResource {

    @Inject
    Event<TicketSubmitted> events;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Ticket CreateTicket(Map<String, Object> params){
        Long personId = ((Number)params.get("personId")).longValue();
        String addressMain = (String) params.get("addressMain");
        String addressDetail = (String) params.get("addressDetail");
        String phone = (String) params.get("phone");
        Ticket ticket = Ticket.persist(personId, addressMain, addressDetail, phone);
        return ticket;
    }

    @GET
    @Path("/{id}")
    public Ticket readTicket(@PathParam("id") long id){
        return Ticket.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket deleteTicket(Long id){
        Ticket t = readTicket(id);
        t.status = TicketStatus.DELETED;
        return t;
    }

    @PUT
    @Path("/{id}")
    public Ticket addItem(@PathParam("id") Long id, TicketItemAdd itemAdd){
        Ticket ticket = readTicket(id);
        if(ticket == null){
            throw new NotFoundException("Ticket not found");
        }
        if(!TicketStatus.OPEN.equals(ticket.status)){
            throw new BadRequestException("Ticket not open");
        }
        if(itemAdd.quantity().intValue() <=0
        || itemAdd.quantity().intValue() >= 99){
            throw new BadRequestException("invalid quantity");
        }
        ticket.addItem(itemAdd);
        events.fire(new TicketSubmitted(
                ticket,
                LocalDateTime.now()
        ));
        return ticket;
    }

    @POST
    @Path("/{id}/submit")
    public Ticket submitTicket(Long id){
        Ticket ticket = readTicket(id);
        if(!TicketStatus.OPEN.equals(ticket.status)){
            throw new BadRequestException("Ticket not open");
        }
        ticket.status = TicketStatus.SUBMITTED;
        ticket.persistAndFlush();
        return ticket;
    }
}
