package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Doktor;
import com.hastatakip.hastatakip.repository.DoktorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoktorService {

    @Autowired
    private DoktorRepository doktorRepository;

    public Doktor doktorEkle(Doktor doktor) {
        return doktorRepository.save(doktor);
    }

    public Doktor doktorGuncelle(Long id, Doktor doktor) {
        Doktor mevcutDoktor = doktorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));
        mevcutDoktor.setAd(doktor.getAd());
        mevcutDoktor.setSoyad(doktor.getSoyad());
        mevcutDoktor.setBranş(doktor.getBranş());
        mevcutDoktor.setTelefon(doktor.getTelefon());
        mevcutDoktor.setEmail(doktor.getEmail());
        return doktorRepository.save(mevcutDoktor);
    }

    public void doktorSil(Long id) {
        doktorRepository.deleteById(id);
    }

    public List<Doktor> tumDoktorlar() {
        return doktorRepository.findAll();
    }

    public Optional<Doktor> doktorById(Long id) {
        return doktorRepository.findById(id);
    }

    public List<Doktor> doktorByBrans(String branş) {
        return doktorRepository.findByBranş(branş);
    }
}

