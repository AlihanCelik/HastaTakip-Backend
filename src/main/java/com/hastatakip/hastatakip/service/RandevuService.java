package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Hasta;
import com.hastatakip.hastatakip.model.Randevu;
import com.hastatakip.hastatakip.repository.HastaRepository;
import com.hastatakip.hastatakip.repository.RandevuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandevuService {

    @Autowired
    private RandevuRepository randevuRepository;

    @Autowired
    private HastaService hastaService;

    public Randevu randevuEkle(Randevu randevu) {
        Hasta hasta = hastaService.hastaBulByTcKimlikAndAdSoyad(
                randevu.getHasta().getTcKimlikNo(),
                randevu.getHasta().getAd(),
                randevu.getHasta().getSoyad()
        );

        if (hasta == null) {
            throw new RuntimeException("Hasta bulunamadı. Lütfen doğru bilgileri girin.");
        }

        List<Randevu> mevcutRandevular = randevuRepository.findByDoktorIdAndRandevuTarihi(randevu.getDoktor().getId(), randevu.getRandevuTarihi());
        if (!mevcutRandevular.isEmpty()) {
            throw new RuntimeException("Bu doktorun o tarihte randevusu var. Lütfen başka bir tarih seçin.");
        }

        randevu.setHasta(hasta);
        return randevuRepository.save(randevu);
    }

    public Randevu randevuGuncelle(Long id, Randevu randevu) {
        Randevu mevcutRandevu = randevuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Randevu bulunamadı"));

        if (!mevcutRandevu.getHasta().getTcKimlikNo().equals(randevu.getHasta().getTcKimlikNo()) ||
                !mevcutRandevu.getHasta().getAd().equalsIgnoreCase(randevu.getHasta().getAd()) ||
                !mevcutRandevu.getHasta().getSoyad().equalsIgnoreCase(randevu.getHasta().getSoyad())) {

            Hasta hasta = hastaService.hastaBulByTcKimlikAndAdSoyad(
                    randevu.getHasta().getTcKimlikNo(),
                    randevu.getHasta().getAd(),
                    randevu.getHasta().getSoyad()
            );

            if (hasta == null) {
                throw new RuntimeException("Hasta bulunamadı. Lütfen doğru bilgileri girin.");
            }
            mevcutRandevu.setHasta(hasta);
        }
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

    public List<Randevu> randevularByHastaTc(String tcKimlikNo) {
        return randevuRepository.findByHasta_TcKimlikNo(tcKimlikNo);
    }
}

