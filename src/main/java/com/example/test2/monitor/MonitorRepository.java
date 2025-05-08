package com.example.test2.monitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
    Optional<Monitor> findMonitorByName(String name);
}
