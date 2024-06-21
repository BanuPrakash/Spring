package com.example.shopapp.handler;

import com.example.shopapp.event.PatientDischargeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationServiceHandler {
    @EventListener
    @Async
    public void processBill(PatientDischargeEvent evt) {
        log.info(Thread.currentThread() + " notification Service :..." + evt.getName() );
    }

    @EventListener
    @Async
    public void processHouseKeeping(PatientDischargeEvent evt) {
        log.info(Thread.currentThread() + " house keeping  Service :..." + evt.getName() );
    }
}
