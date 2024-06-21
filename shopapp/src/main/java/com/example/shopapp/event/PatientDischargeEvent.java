package com.example.shopapp.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PatientDischargeEvent extends ApplicationEvent {
    private int id;
    private String name;
    public PatientDischargeEvent(Object source, int id, String name) {
        super(source);
        this.id = id;
        this.name = name;
    }

}
