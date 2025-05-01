package com.hastatakip.hastatakip.repository;

import com.hastatakip.hastatakip.model.Randevu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RandevuRepository extends JpaRepository<Randevu, Long> {
    List<Randevu> findByHastaId(Long hastaId);
    List<Randevu> findByDoktorId(Long doktorId);
}

