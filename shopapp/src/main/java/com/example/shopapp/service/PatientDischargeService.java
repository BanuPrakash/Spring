package com.example.shopapp.service;

import com.example.shopapp.event.PatientDischargeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientDischargeService {
    private final ApplicationEventPublisher eventPublisher;

    // OCP
    public String dischargePatient(int id, String name) {
        log.info("Patient discharge process : { }", name );
        eventPublisher.publishEvent(new PatientDischargeEvent(this, id, name));
        log.info("Patient discharge completed {} ", name);
        return  "Patient " + name + " dischared Successfully!!!";
    }
}
