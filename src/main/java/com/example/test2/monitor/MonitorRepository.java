package com.example.test2.monitor;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRepository extends JpaRepository<MonitorEntity, Long> {
    Optional<MonitorEntity> findByName(String name); // Use Optional to handle the case where the monitor is not found


}
