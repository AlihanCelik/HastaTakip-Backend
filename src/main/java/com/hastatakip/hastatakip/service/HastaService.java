package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Hasta;
import com.hastatakip.hastatakip.repository.HastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HastaService {

    @Autowired
    private HastaRepository hastaRepository;

    public Hasta hastaEkle(Hasta hasta) {
        return hastaRepository.save(hasta);
    }

    public Hasta hastaGuncelle(Long id, Hasta hasta) {
        Hasta mevcutHasta = hastaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));
        mevcutHasta.setAd(hasta.getAd());
        mevcutHasta.setSoyad(hasta.getSoyad());
        mevcutHasta.setTcKimlikNo(hasta.getTcKimlikNo());
        mevcutHasta.setDogumTarihi(hasta.getDogumTarihi());
        mevcutHasta.setCinsiyet(hasta.getCinsiyet());
        mevcutHasta.setTelefon(hasta.getTelefon());
        mevcutHasta.setEmail(hasta.getEmail());
        mevcutHasta.setAdres(hasta.getAdres());
        mevcutHasta.setAcilDurumKisi(hasta.getAcilDurumKisi());
        return hastaRepository.save(mevcutHasta);
    }

    public void hastaSil(Long id) {
        hastaRepository.deleteById(id);
    }

    public List<Hasta> tumHastalar() {
        return hastaRepository.findAll();
    }

    public Optional<Hasta> hastaById(Long id) {
        return hastaRepository.findById(id);
    }
}


