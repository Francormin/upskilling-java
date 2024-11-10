package com.example.demojdbctemplate.repository;

import com.example.demojdbctemplate.model.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String createTableSql = "CREATE TABLE Ticket (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "description VARCHAR(255)," +
            "status VARCHAR(255)" +
            ");";

        jdbcTemplate.execute(createTableSql);
    }

    public void insertTicket(String description, String status) {
        String insertTicketSql = "INSERT INTO Ticket (description, status)" +
            "VALUES (?, ?);";

        jdbcTemplate.update(insertTicketSql, description, status);
    }

    public List<Ticket> getAllTickets() {
        String getAllTicketsSql = "SELECT * FROM Ticket";
        return jdbcTemplate.query(getAllTicketsSql, new TicketRowMapper());
    }

    private static final class TicketRowMapper implements RowMapper<Ticket> {

        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(rs.getLong("id"));
            ticket.setDescription(rs.getString("description"));
            ticket.setStatus(rs.getString("status"));
            return ticket;
        }

    }

}
