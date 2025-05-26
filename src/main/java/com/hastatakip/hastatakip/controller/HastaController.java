package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Hasta;
import com.hastatakip.hastatakip.service.HastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/hasta")
public class HastaController {

    @Autowired
    private HastaService hastaService;

    @PostMapping("/ekle")
    public ResponseEntity<Void> hastaEkle(@RequestBody Hasta hasta) {
        try {
            hastaService.hastaEkle(hasta);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<Void> hastaGuncelle(@PathVariable Long id, @RequestBody Hasta hasta) {
        try {
            hastaService.hastaGuncelle(id, hasta);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> hastaSil(@PathVariable Long id) {
        try {
            hastaService.hastaSil(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tumHastalar")
    public ResponseEntity<List<Hasta>> tumHastalar() {
        try {
            List<Hasta> hastalar = hastaService.tumHastalar();
            return new ResponseEntity<>(hastalar, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hasta> hastaById(@PathVariable Long id) {
        try {
            Hasta hasta = hastaService.hastaById(id);
            if (hasta != null) {
                return new ResponseEntity<>(hasta, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
