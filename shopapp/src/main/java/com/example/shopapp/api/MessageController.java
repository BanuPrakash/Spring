package com.example.shopapp.api;

import com.example.shopapp.dto.Message;
import com.example.shopapp.dto.MessageView;
import com.example.shopapp.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @JsonView(MessageView.Summary.class)
    @GetMapping()
    public List<Message> getAllMessages() {
        return messageService.getAll();
    }

    @JsonView(MessageView.SummaryWithRecipients.class)
    @GetMapping("/with-recipients")
    public List<Message> getAllMessagesWithRecipients() {
        return messageService.getAll();
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return this.messageService.get(id);
    }

    @PostMapping()
    public Message create(@RequestBody Message message) {
        return this.messageService.create(message);
    }

}
