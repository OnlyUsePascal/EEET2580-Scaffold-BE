package com.example.test2.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitorServiceImpl implements MonitorService {
    private final MonitorRepository monitorRepository;

    @Autowired
    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    public Optional<List<Monitor>> getAllMonitors() {
        List<Monitor> results = monitorRepository.findAll();
        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results);
        }
    }
}
