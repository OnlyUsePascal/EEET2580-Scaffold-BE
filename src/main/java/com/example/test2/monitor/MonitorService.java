package com.example.test2.monitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class MonitorService {
    private final MonitorRepository monitorRepository;

    @Autowired
    public MonitorService(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }
    public Page<MonitorEntity> getAllMonitors(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return monitorRepository.findAll(paging);
    }

    public MonitorEntity getMonitorById(Long id) {
        return monitorRepository.findById(id).orElse(null);
    }

    public MonitorEntity createdMonitor(CreateMonitorDTO monitor) {
        // Check if the monitor already exists in the database
        if (monitorRepository.findByName(monitor.getName()).isPresent()) {
            throw new IllegalArgumentException("Monitor with name " + monitor.getName() + " already exists.");
        }
        // Create a new MonitorEntity object and set its properties
        MonitorEntity newMonitor = new MonitorEntity();
        newMonitor.setName(monitor.getName());
        newMonitor.setBrand(monitor.getBrand());
        newMonitor.setPrice(monitor.getPrice());
        // Save the new monitor to the database
        return monitorRepository.save(newMonitor);
    }

    public void deleteMonitorbyId(Long id) {
        var monitor = monitorRepository.findById(id).orElse(null);
        if (monitor == null) {
            throw new IllegalArgumentException("Monitor with ID " + id + " does not exist.");
        }
        monitorRepository.deleteById(id);
    }

    public MonitorEntity updateMonitor(Long id, CreateMonitorDTO monitor) {
        MonitorEntity existingMonitor = monitorRepository.findById(id).orElse(null);
        if (existingMonitor == null) {
            throw new IllegalArgumentException("Monitor with ID " + id + " does not exist.");
        }

        existingMonitor.setName(monitor.getName());
        existingMonitor.setBrand(monitor.getBrand());
        existingMonitor.setPrice(monitor.getPrice());
        return monitorRepository.save(existingMonitor);
    }

}
