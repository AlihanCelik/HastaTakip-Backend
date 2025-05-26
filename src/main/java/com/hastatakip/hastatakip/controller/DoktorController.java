package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Doktor;
import com.hastatakip.hastatakip.service.DoktorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/doktor")
public class DoktorController {

    private final DoktorService doktorService;

    public DoktorController(DoktorService doktorService) {
        this.doktorService = doktorService;
    }

    @PostMapping("/ekle")
    public ResponseEntity<String> doktorEkle(@RequestBody Doktor doktor) {
        try {
            doktorService.doktorEkle(doktor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Doktor başarıyla eklendi.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ekleme sırasında hata oluştu: " + e.getMessage());
        }
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<String> doktorGuncelle(@PathVariable Long id, @RequestBody Doktor doktor) {
        try {
            doktorService.doktorGuncelle(id, doktor);
            return ResponseEntity.ok("Doktor başarıyla güncellendi.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Güncelleme sırasında hata oluştu: " + e.getMessage());
        }
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<String> doktorSil(@PathVariable Long id) {
        try {
            doktorService.doktorSil(id);
            return ResponseEntity.ok("Doktor başarıyla silindi.");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Silme sırasında hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping("/tumDoktorlar")
    public ResponseEntity<List<Doktor>> tumDoktorlar() {
        try {
            return ResponseEntity.ok(doktorService.tumDoktorlar());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doktor> doktorById(@PathVariable Long id) {
        try {
            Doktor doktor = doktorService.doktorById(id);
            if (doktor != null) {
                return ResponseEntity.ok(doktor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/brans/{brans}")
    public ResponseEntity<List<Doktor>> doktorByBrans(@PathVariable String brans) {
        try {
            return ResponseEntity.ok(doktorService.doktorByBrans(brans));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
