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
        return ResponseEntity.ok(page);
    }

    @PostMapping("/monitors")
    public ResponseEntity<Monitor> createMonitor(@Valid @RequestBody MonitorDTO monitorDTO) throws Exception {
        return ResponseEntity.ok(monitorService.createMonitor(monitorDTO));
    }


    @PutMapping("/monitors/{id}")
    public ResponseEntity<Monitor> updateMonitor(
            @PathVariable int id,
            @Valid @RequestBody MonitorDTO monitorDTO
    ) {
        return ResponseEntity.ok(monitorService.updateMonitor(id, monitorDTO));
    }

    @DeleteMapping("/monitors/{id}")
    public ResponseEntity<String> deleteMonitorById(@PathVariable int id) {
        monitorService.deleteMonitorById(id);
        return ResponseEntity.ok("Deleted product with id:" + id);
    }
}
