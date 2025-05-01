package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Randevu;
import com.hastatakip.hastatakip.service.RandevuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/randevu")
public class RandevuController {

    @Autowired
    private RandevuService randevuService;

    @PostMapping("/ekle")
    public ResponseEntity<Randevu> randevuEkle(@RequestBody Randevu randevu) {
        return new ResponseEntity<>(randevuService.randevuEkle(randevu), HttpStatus.CREATED);
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<Randevu> randevuGuncelle(@PathVariable Long id, @RequestBody Randevu randevu) {
        return new ResponseEntity<>(randevuService.randevuGuncelle(id, randevu), HttpStatus.OK);
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> randevuSil(@PathVariable Long id) {
        randevuService.randevuSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tumRandevular")
    public ResponseEntity<List<Randevu>> tumRandevular() {
        return new ResponseEntity<>(randevuService.tumRandevular(), HttpStatus.OK);
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<Randevu>> randevularByHasta(@PathVariable Long hastaId) {
        return new ResponseEntity<>(randevuService.randevularByHasta(hastaId), HttpStatus.OK);
    }
}

