package com.example.test2.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/monitor")
public class MonitorController {

    private final MonitorService monitorService;

    @Autowired
    public MonitorController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }
    

    @GetMapping("")
    public ResponseEntity<Page<MonitorEntity>> getAllMonitors(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        
        Page<MonitorEntity> monitors = monitorService.getAllMonitors(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(monitors);
    }

    @PostMapping("")
    public ResponseEntity<MonitorEntity> createMonitor(@RequestBody CreateMonitorDTO monitor) {
        MonitorEntity createdMonitor = monitorService.createdMonitor(monitor);
        return ResponseEntity.ok(createdMonitor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable Long id) {
        monitorService.deleteMonitorbyId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitorEntity> updateMonitor(@PathVariable Long id, @RequestBody CreateMonitorDTO monitor) {
        MonitorEntity updatedMonitor = monitorService.updateMonitor(id, monitor);
        return ResponseEntity.ok(updatedMonitor);
    }


}
