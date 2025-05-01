package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Randevu;
import com.hastatakip.hastatakip.repository.RandevuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandevuService {

    @Autowired
    private RandevuRepository randevuRepository;

    public Randevu randevuEkle(Randevu randevu) {
        return randevuRepository.save(randevu);
    }

    public Randevu randevuGuncelle(Long id, Randevu randevu) {
        Randevu mevcutRandevu = randevuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        mevcutRandevu.setRandevuTarihi(randevu.getRandevuTarihi());
        mevcutRandevu.setDurum(randevu.getDurum());
        return randevuRepository.save(mevcutRandevu);
    }

    public void randevuSil(Long id) {
        randevuRepository.deleteById(id);
    }

    public List<Randevu> tumRandevular() {
        return randevuRepository.findAll();
    }

    public List<Randevu> randevularByHasta(Long hastaId) {
        return randevuRepository.findByHastaId(hastaId);
    }
}

