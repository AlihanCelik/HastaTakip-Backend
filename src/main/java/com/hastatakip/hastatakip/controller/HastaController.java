package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Hasta;
import com.hastatakip.hastatakip.service.HastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hasta")
public class HastaController {

    @Autowired
    private HastaService hastaService;

    @PostMapping("/ekle")
    public ResponseEntity<Hasta> hastaEkle(@RequestBody Hasta hasta) {
        return new ResponseEntity<>(hastaService.hastaEkle(hasta), HttpStatus.CREATED);
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<Hasta> hastaGuncelle(@PathVariable Long id, @RequestBody Hasta hasta) {
        return new ResponseEntity<>(hastaService.hastaGuncelle(id, hasta), HttpStatus.OK);
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> hastaSil(@PathVariable Long id) {
        hastaService.hastaSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tumHastalar")
    public ResponseEntity<List<Hasta>> tumHastalar() {
        return new ResponseEntity<>(hastaService.tumHastalar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hasta> hastaById(@PathVariable Long id) {
        return hastaService.hastaById(id)
                .map(hasta -> new ResponseEntity<>(hasta, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

