package com.example.demojdbctemplate.controller;

import com.example.demojdbctemplate.model.Ticket;
import com.example.demojdbctemplate.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/initialize")
    public void initializeDatabase() {
        ticketService.initializeDatabase();
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

}
