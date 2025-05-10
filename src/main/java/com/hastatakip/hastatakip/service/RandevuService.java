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
        List<Randevu> mevcutRandevular = randevuRepository.findByDoktorIdAndRandevuTarihi(randevu.getDoktor().getId(), randevu.getRandevuTarihi());
        if (!mevcutRandevular.isEmpty()) {
            throw new RuntimeException("Bu doktorun o tarihte randevusu var. Lütfen başka bir tarih seçin.");
        }

        return randevuRepository.save(randevu);
    }

    public Randevu randevuGuncelle(Long id, Randevu randevu) {
        Randevu mevcutRandevu = randevuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));
        if (!mevcutRandevu.getRandevuTarihi().equals(randevu.getRandevuTarihi())) {
            List<Randevu> mevcutRandevular = randevuRepository.findByDoktorIdAndRandevuTarihi(randevu.getDoktor().getId(), randevu.getRandevuTarihi());
            if (!mevcutRandevular.isEmpty()) {
                throw new RuntimeException("Bu doktorun o tarihte randevusu var. Lütfen başka bir tarih seçin.");
            }
        }

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

