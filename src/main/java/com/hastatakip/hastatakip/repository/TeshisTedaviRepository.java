package com.hastatakip.hastatakip.repository;

import com.hastatakip.hastatakip.model.TeshisTedavi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeshisTedaviRepository extends JpaRepository<TeshisTedavi, Long> {
    List<TeshisTedavi> findByHastaId(Long hastaId);
}

