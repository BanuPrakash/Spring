package com.example.shopapp.api;

import com.example.shopapp.dto.PatientDischargeRequest;
import com.example.shopapp.service.PatientDischargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@RequestMapping("api/discharge")
@RequiredArgsConstructor
public class DischargeController {
    private final PatientDischargeService service;

    @PostMapping()
    public String discharge(@RequestBody PatientDischargeRequest request) {
        return service.dischargePatient(request.getId(), request.getName());
    }
}
