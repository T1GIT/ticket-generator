package com.generator.app.service;


import com.generator.app.entity.Theme;
import com.generator.app.entity.Ticket;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.stereotype.Service;

@Service
public interface TicketService extends ServiceInterface<Ticket> {

    void deleteByTheme(Theme theme);
}
