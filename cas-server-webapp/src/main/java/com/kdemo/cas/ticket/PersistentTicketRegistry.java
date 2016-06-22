package com.kdemo.cas.ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.AbstractTicketRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.kdemo.cas.ticket.dao.TicketDao;
import com.kdemo.cas.ticket.dto.TicketDto;

public class PersistentTicketRegistry extends AbstractTicketRegistry  {
	@Autowired
	private TicketDao ticketDao;
	
    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException if the Ticket is null.
     */
    @Override
    public void addTicket(final Ticket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");

        TicketDto ticketDto = new TicketDto(ticket.getId(), toBytes(ticket));
        ticketDao.addTicket(ticketDto);
    }

    public Ticket getTicket(final String ticketId) {
        if (ticketId == null) {
            return null;
        }

        logger.debug("Attempting to retrieve ticket [{}]", ticketId);
        
        
        TicketDto ticketDto = ticketDao.getTicketById(ticketId);
       
        Ticket ticket = null;
        if (ticketDto != null) {
            logger.debug("Ticket [{}] found in registry.", ticketId);
            ticket = toTicket(ticketDto.getTicket());
        }

        return ticket;
    }

    public boolean deleteTicket(final String ticketId) {
        if (ticketId == null) {
            return false;
        }
        logger.debug("Removing ticket [{}] from registry", ticketId);
        return ticketDao.deleteTicketById(ticketId) != 0;
    }

    public Collection<Ticket> getTickets() {
		List<TicketDto> ticketDtos = ticketDao.getTickets();
		Collection<Ticket> tickets = new ArrayList<Ticket>();

		for (TicketDto ticketDto : ticketDtos) {
			Ticket ticket = toTicket(ticketDto.getTicket());
			tickets.add(ticket);
		}

		return tickets;
    }

    public int sessionCount() {
        int count = 0;
        
        Collection<Ticket> tickets = getTickets();
        for (Ticket t : tickets) {
            if (t instanceof TicketGrantingTicket) {
                count++;
            }
        }
        return count;
    }

    public int serviceTicketCount() {
        int count = 0;
        
        Collection<Ticket> tickets = getTickets();
        for (Ticket t : tickets) {
            if (t instanceof ServiceTicket) {
                count++;
            }
        }
        return count;
    }
    
    
    private byte[] toBytes(Ticket ticket) {
    	return SerializationUtils.serialize(ticket);
    }
    
    private Ticket toTicket(byte[] bytes) {
    	return (Ticket) SerializationUtils.deserialize(bytes);
    }
}
