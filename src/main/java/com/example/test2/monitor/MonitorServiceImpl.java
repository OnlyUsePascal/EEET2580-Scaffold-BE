package com.example.test2.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Optional<Monitor> getMonitorById(int id) {
        Monitor monitor = monitorRepository.findById(id).orElse(null);
        if (monitor == null) {
            return Optional.empty();
        } else {
            return Optional.of(monitor);
        }
    }

    @Override
    public Optional<Monitor> getMonitorByName(String name) {
        return monitorRepository.findMonitorByName(name);
    }

    @Override
    public Monitor createMonitor(Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    @Override
    public Monitor updateMonitor(Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    @Override
    public void deleteMonitorById(int id) {
        monitorRepository.deleteById(id);
    }

    @Override
    public Page<Monitor> getAllMonitorsByPagination(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable request = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

        return monitorRepository.findAll(request);
    }
}
