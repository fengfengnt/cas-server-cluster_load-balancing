package com.kdemo.cas.ticket.dao;

import java.util.List;

import com.kdemo.cas.ticket.dto.TicketDto;

public interface TicketDao {
	int addTicket(TicketDto ticket);
	TicketDto getTicketById(String id);
	int deleteTicketById(String id);
	List<TicketDto> getTickets();
}
