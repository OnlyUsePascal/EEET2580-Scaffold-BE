package com.example.test2.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class MonitorController {
    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/monitors")
    public Optional<List<Monitor>> getAllMonitors() {
        return monitorService.getAllMonitors();
    }
}
