package com.example.test2.monitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, Integer> {
}
