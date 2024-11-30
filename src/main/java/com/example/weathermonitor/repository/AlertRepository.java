package com.example.weathermonitor.repository;

import com.example.weathermonitor.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserEmail(String userEmail);
    List<Alert> findAll();
}
