package com.generator.app.service.impl;

import com.generator.app.entity.Theme;
import com.generator.app.entity.Ticket;
import com.generator.app.repository.TicketRepository;
import com.generator.app.service.TicketService;
import com.generator.app.util.abstractService.impl.ServiceInterfaceImpl;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends ServiceInterfaceImpl<Ticket, TicketRepository> implements TicketService {
    public TicketServiceImpl(TicketRepository repository) {
        super(repository);
    }

    @Override
    public void deleteByTheme(Theme theme) {
        repository.deleteAllByThemeId(theme.getId());
    }
}
