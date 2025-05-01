package com.hastatakip.hastatakip.repository;

import com.hastatakip.hastatakip.model.Doktor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoktorRepository extends JpaRepository<Doktor, Long> {
    Optional<Doktor> findByEmail(String email);
    List<Doktor> findByBranş(String branş);
}