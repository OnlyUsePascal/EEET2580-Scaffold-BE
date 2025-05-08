package com.example.test2.monitor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MonitorService {
    Optional<List<Monitor>> getAllMonitors();

    Optional<Monitor> getMonitorById(int id);

    Optional<Monitor> getMonitorByName(String name);

    Monitor createMonitor(Monitor monitor);

    Monitor updateMonitor(Monitor monitor);

    void deleteMonitorById(int id);
}
