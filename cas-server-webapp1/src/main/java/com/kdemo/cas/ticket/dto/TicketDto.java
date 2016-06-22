package com.kdemo.cas.ticket.dto;

import java.util.Arrays;

public class TicketDto {
	private String ticketId;
	private byte[] ticket;
	
	public TicketDto() { }
	
	public TicketDto(String ticketId, byte[] ticket) {
		this.ticketId = ticketId;
		this.ticket = ticket;
	}
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public byte[] getTicket() {
		return ticket;
	}
	public void setTicket(byte[] ticket) {
		this.ticket = ticket;
	}
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", ticket=" + Arrays.toString(ticket) + "]";
	}
}
