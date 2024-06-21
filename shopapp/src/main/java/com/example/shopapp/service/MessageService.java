package com.example.shopapp.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.shopapp.dto.Message;
import com.example.shopapp.dto.Person;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final List<Message> messages = new ArrayList<>();

    public MessageService() {
        Person b = new Person(1L, "Brian", "King", "brain@abc.com", "1 Good street", "69003", "Lyon", "France");
        Person s = new Person(2L, "Steve", "Jobs", "steve@abc.com", "2 Obama street", "1000", "Brussel", "Belgium");
        Person a = new Person(3L, "Anna", "Cram", "anna@abc.com", "3 Warren street", "10011", "New York", "USA");

        Message info = new Message(1L, "Info", "This is an information message", b, s, a);
        Message warning = new Message(2L, "Warning", "This is a warning message", s, a);
        Message alert = new Message(3L, "Alert", "This is an alert message", s, b);

        messages.add(info);
        messages.add(warning);
        messages.add(alert);
    }

    public List<Message> getAll() {
        return messages;
    }

    public Message get(Long id) {
        return this.messages.stream().filter((m) -> m.getId() == id).findFirst().get();
    }

    public Message create(Message message) {
        this.messages.add(message);
        return message;
    }
}