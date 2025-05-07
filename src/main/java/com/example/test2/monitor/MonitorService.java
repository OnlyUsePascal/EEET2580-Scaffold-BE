package com.example.test2.monitor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MonitorService {
    Optional<List<Monitor>> getAllMonitors();
}
