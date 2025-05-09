package com.example.test2.monitor;

import com.example.test2.monitor.dto.MonitorDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Page<Monitor>> getAllMonitorsByPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        Page<Monitor> page = monitorService.getAllMonitorsByPagination(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(page); // 200
    }

    @PostMapping("/monitors")
    public ResponseEntity<Monitor> createMonitor(@Valid @RequestBody MonitorDTO monitorDTO) {
        if (monitorService.getMonitorByName(monitorDTO.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        Monitor monitor = new Monitor();
        monitor.setName(monitorDTO.getName());
        monitor.setBrand(monitorDTO.getBrand());
        monitor.setPrice(monitorDTO.getPrice());

        Monitor createdMonitor = monitorService.createMonitor(monitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMonitor); // 201
    }

    @PutMapping("/monitors/{id}")
    public ResponseEntity<Monitor> updateMonitor(
            @PathVariable int id,
            @Valid @RequestBody Monitor monitor
    ) {
        Optional<Monitor> existingMonitor = monitorService.getMonitorById(id);
        if (existingMonitor.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        Optional<Monitor> duplicateName = monitorService.getMonitorByName(monitor.getName());
        if (duplicateName.isPresent() && duplicateName.get().getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        monitor.setId(id);
        Monitor updated = monitorService.updateMonitor(monitor);
        return ResponseEntity.ok(updated); // 200 OK
    }

    @DeleteMapping("/monitors/{id}")
    public ResponseEntity<Void> deleteMonitorById(@PathVariable int id) {
        Optional<Monitor> monitor = monitorService.getMonitorById(id);
        if (monitor.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }

        monitorService.deleteMonitorById(id);

        // Double-check deletion (optional — may not be needed if `deleteById()` is safe)
        boolean stillExists = monitorService.getMonitorById(id).isPresent();
        if (stillExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        return ResponseEntity.noContent().build(); // 204
    }
}
