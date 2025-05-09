package com.example.test2.monitor;

import com.example.test2.monitor.dto.MonitorDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MonitorService {
    Optional<List<Monitor>> getAllMonitors();

    Optional<Monitor> getMonitorById(int id);

    Optional<Monitor> getMonitorByName(String name);

    Monitor createMonitor(MonitorDTO monitor);

    Monitor updateMonitor(int id, MonitorDTO monitor);

    void deleteMonitorById(int id);

    Page<Monitor> getAllMonitorsByPagination(int pageNumber, int pageSize, String sortBy, String sortOrder);
}
