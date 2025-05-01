package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.TeshisTedavi;
import com.hastatakip.hastatakip.repository.TeshisTedaviRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeshisTedaviService {

    @Autowired
    private TeshisTedaviRepository teshisTedaviRepository;

    public TeshisTedavi teshisTedaviEkle(TeshisTedavi teshisTedavi) {
        return teshisTedaviRepository.save(teshisTedavi);
    }

    public List<TeshisTedavi> teshisTedaviByHasta(Long hastaId) {
        return teshisTedaviRepository.findByHastaId(hastaId);
    }
}

