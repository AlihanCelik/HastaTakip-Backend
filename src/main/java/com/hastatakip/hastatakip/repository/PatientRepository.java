package com.hastatakip.hastatakip.repository;

import com.hastatakip.hastatakip.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
