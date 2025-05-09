package com.example.test2.monitor;

import com.example.test2.monitor.dto.MonitorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Monitor createMonitor(MonitorDTO monitor) {
        var oldMonitor = monitorRepository.findMonitorByName(monitor.getName());
        if (oldMonitor.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monitor with name " + monitor.getName() + " already exists");
        }

        return monitorRepository.save(
                Monitor
                        .builder()
                        .name(monitor.getName())
                        .brand(monitor.getBrand())
                        .price(monitor.getPrice())
                        .build()
        );
    }

    @Override
    public Monitor updateMonitor(int id, MonitorDTO monitor) {
        var oldMonitor = monitorRepository.findMonitorByName(monitor.getName());
        if (oldMonitor.isPresent() && oldMonitor.get().getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monitor with name " + monitor.getName() + " already exists");
        }
        var monitorToUpdate = monitorRepository.findById(id);
        if (monitorToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitor with id " + id + " not found");
        }
        Monitor updatedMonitor = monitorToUpdate.get();
        updatedMonitor.setName(monitor.getName());
        updatedMonitor.setBrand(monitor.getBrand());
        updatedMonitor.setPrice(monitor.getPrice());

        return monitorRepository.save(updatedMonitor);
    }

    @Override
    public void deleteMonitorById(int id) {
        var monitor = monitorRepository.findById(id);
        if (monitor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitor with id " + id + " not found");
        }
        monitorRepository.deleteById(id);
    }

    @Override
    public Page<Monitor> getAllMonitorsByPagination(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable request = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

        return monitorRepository.findAll(request);
    }
}
