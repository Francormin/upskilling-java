package com.example.demojdbctemplate.service;

import com.example.demojdbctemplate.model.Ticket;
import com.example.demojdbctemplate.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void initializeDatabase() {
        ticketRepository.createTable();
        ticketRepository.insertTicket("Issue with login", "OPEN");
        ticketRepository.insertTicket("Bug in checkout process", "IN_PROGRESS");
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }

}
