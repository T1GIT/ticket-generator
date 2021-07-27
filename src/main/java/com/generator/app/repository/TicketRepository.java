package com.generator.app.repository;

import com.generator.app.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    void deleteAllByThemeId(long themId);
}
