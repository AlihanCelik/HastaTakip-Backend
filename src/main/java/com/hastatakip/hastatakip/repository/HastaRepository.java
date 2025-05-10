package com.hastatakip.hastatakip.repository;

import com.hastatakip.hastatakip.model.Hasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HastaRepository extends JpaRepository<Hasta, Long> {
    Optional<Hasta> findByTcKimlikNo(String tcKimlikNo);
    Hasta findByTcKimlikNoAndAdAndSoyad(String tcKimlikNo, String ad, String soyad);

}

