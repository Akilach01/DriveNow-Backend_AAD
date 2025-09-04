package org.example.drivenow_carrental_aad.controller;

import org.example.drivenow_carrental_aad.service.RentDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class RentDetailsController {

    private final RentDetailService service;

    public RentDetailsController(RentDetailService service) {
        this.service = service;
    }

}
