package com.generator.app.controller;

import com.generator.app.entity.*;
import com.generator.app.service.*;
import com.generator.app.util.exception.NotEnoughTaskException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private int numIndex = 0;
    private final ThemeService themeService;
    private final TheoryService theoryService;
    private final PracticeService practiceService;
    private final TicketService ticketService;

    public TicketController(ThemeService themeService, TheoryService theoryService, PracticeService practiceService, TicketService ticketService) {
        this.themeService = themeService;
        this.theoryService = theoryService;
        this.practiceService = practiceService;
        this.ticketService = ticketService;
    }

    @GetMapping("/theme")
    public Map<String, Object> getAllByTheme(@RequestParam long themeId) {
        Map<String, Object> resMap = new HashMap<>();
        Theme theme = themeService.getById(themeId);
        List<Ticket> tickets = theme.getTickets();
        tickets.sort(Comparator.comparingInt(Ticket::getNum));
        resMap.put("tickets", tickets);
        Map<String, Integer> amountMap = new HashMap<>();
        amountMap.put("theory", theme.getTheories().size());
        amountMap.put("practice", theme.getPractices().size());
        resMap.put("amount", amountMap);
        return resMap;
    }

    @GetMapping
    public Map<String, Object> get(@RequestParam long ticketId) {
        Map<String, Object> resMap = new HashMap<>();
        Ticket ticket = ticketService.getById(ticketId);
        resMap.put("theories", ticket.getTheories());
        resMap.put("practices", ticket.getPractices());
        return resMap;
    }

    @GetMapping("/create")
    @Transactional
    public List<Ticket> create(
            @RequestParam long themeId,
            @RequestParam int amount,
            @RequestParam int theoryAmount,
            @RequestParam int practiceAmount) throws NotEnoughTaskException {
        Theme theme = themeService.getById(themeId);
        ticketService.deleteByTheme(theme);
        for (int i = 0; i < amount; i++) {
            Ticket ticket = new Ticket();
            ticket.setNum(i + 1);
            theme.addTicket(ticket);
            List<Theory> theoryList = theoryService.getRandom(theme, theoryAmount);
            List<Practice> practiceList = practiceService.getRandom(theme, practiceAmount);
            theoryList.forEach(ticket::addTheory);
            practiceList.forEach(ticket::addPractice);
        }
        themeService.update(theme);
        return theme.getTickets();
    }

    @GetMapping("/doc")
    public void downloadTicket(@RequestParam long ticketId, HttpServletResponse response) throws IOException {
        Ticket ticket = ticketService.getById(ticketId);
        XWPFDocument document = new XWPFDocument();
        fillTicket(document, ticket);
        response.setContentType("application/msword");
        document.write(response.getOutputStream());
        response.flushBuffer();
        numIndex = 0;
    }

    @GetMapping("/doc/theme")
    public void downloadTicketByTheme(@RequestParam long themeId, HttpServletResponse response) throws IOException {
        Theme theme = themeService.getById(themeId);
        XWPFDocument document = new XWPFDocument();
        theme.getTickets().forEach(ticket -> fillTicket(document, ticket));
        response.setContentType("application/msword");
        document.write(response.getOutputStream());
        response.flushBuffer();
        numIndex = 0;
    }

    private void fillTicket(XWPFDocument document, Ticket ticket) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(22);
        run.setBold(true);
        run.setText(ticket.getTheme().getSubject().getName());
        run.addBreak();
        run = paragraph.createRun();
        run.setFontSize(20);
        run.setText(ticket.getTheme().getName());
        run.addBreak();
        run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Билет №" + ticket.getNum());
        fillTaskList(document, "Теория", ticket.getTheories());
        fillTaskList(document, "Практика", ticket.getPractices());
    }

    private void fillTaskList(XWPFDocument document, String name, List<? extends Task> tasks) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setFontSize(16);
        run.setBold(true);
        run.setText(name);
        addEnumTasks(document, tasks);
    }

    private void addEnumTasks(XWPFDocument document, List<? extends Task> tasks) {
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(numIndex++));
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewLvlText().setVal("%1.");
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = document.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        BigInteger numID = numbering.addNum(abstractNumID);
        for (Task task : tasks) {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setNumID(numID);
            paragraph.setIndentationLeft(1);
            XWPFRun run=paragraph.createRun();
            run.setFontSize(12);
            run.setText(task.getText());
        }
    }

}
