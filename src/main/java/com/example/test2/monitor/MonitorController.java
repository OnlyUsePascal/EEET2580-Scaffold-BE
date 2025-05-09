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
        return new ResponseEntity<>(monitorService.getAllMonitorsByPagination(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @PostMapping("/monitors")
    public ResponseEntity<?> createMonitor(@Valid @RequestBody MonitorDTO monitorDTO) {
        Optional<Monitor> existing = monitorService.getMonitorByName(monitorDTO.getName());

        if (existing.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Monitor name already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        Monitor monitor = new Monitor();
        monitor.setName(monitorDTO.getName());
        monitor.setBrand(monitorDTO.getBrand());
        monitor.setPrice(monitorDTO.getPrice());

        Monitor createdMonitor = monitorService.createMonitor(monitor);
        return new ResponseEntity<>(createdMonitor, HttpStatus.CREATED);
    }

    @PutMapping("/monitors/{id}")
    public ResponseEntity<?> updateMonitor(@PathVariable int id, @Valid @RequestBody Monitor monitor) {
        Monitor result = monitorService.getMonitorById(id).orElse(null);

        if (result == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Monitor id not found - " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Optional<Monitor> existing = monitorService.getMonitorByName(monitor.getName());

        if (existing.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Monitor name already exists, use other name");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        monitor.setId(id);
        Monitor updatedMonitor = monitorService.updateMonitor(monitor);
        return new ResponseEntity<>(updatedMonitor, HttpStatus.OK);
    }

    @DeleteMapping("/monitors/{id}")
    public ResponseEntity<?> deleteMonitorById(@PathVariable int id) {
        monitorService.deleteMonitorById(id);

        boolean isExisted = monitorService.getMonitorById(id).isPresent();

        if (isExisted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to delete monitor with id: " + id);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
