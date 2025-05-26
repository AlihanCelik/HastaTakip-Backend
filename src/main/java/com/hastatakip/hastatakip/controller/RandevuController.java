package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Randevu;
import com.hastatakip.hastatakip.service.RandevuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/randevu")
public class RandevuController {

    @Autowired
    private RandevuService randevuService;

    @PostMapping("/ekle")
    public ResponseEntity<?> randevuEkle(@RequestBody Randevu randevu) {
        try {
            Randevu yeniRandevu = randevuService.randevuEkle(randevu);
            return new ResponseEntity<>(yeniRandevu, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası: " + e.getMessage());
        }
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<?> randevuGuncelle(@PathVariable Long id, @RequestBody Randevu randevu) {
        try {
            Randevu guncellenmis = randevuService.randevuGuncelle(id, randevu);
            return new ResponseEntity<>(guncellenmis, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası: " + e.getMessage());
        }
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<?> randevuSil(@PathVariable Long id) {
        try {
            randevuService.randevuSil(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası: " + e.getMessage());
        }
    }

    @GetMapping("/tumRandevular")
    public ResponseEntity<?> tumRandevular() {
        try {
            List<Randevu> list = randevuService.tumRandevular();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası: " + e.getMessage());
        }
    }

    @GetMapping("/hasta/{tcKimlikNo}")
    public ResponseEntity<?> randevularByHastaTc(@PathVariable String tcKimlikNo) {
        try {
            List<Randevu> list = randevuService.randevularByHastaTc(tcKimlikNo);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Veritabanı hatası: " + e.getMessage());
        }
    }
}
