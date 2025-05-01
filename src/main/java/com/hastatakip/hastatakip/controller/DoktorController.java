package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.Doktor;
import com.hastatakip.hastatakip.service.DoktorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doktor")
public class DoktorController {

    @Autowired
    private DoktorService doktorService;

    @PostMapping("/ekle")
    public ResponseEntity<Doktor> doktorEkle(@RequestBody Doktor doktor) {
        return new ResponseEntity<>(doktorService.doktorEkle(doktor), HttpStatus.CREATED);
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<Doktor> doktorGuncelle(@PathVariable Long id, @RequestBody Doktor doktor) {
        return new ResponseEntity<>(doktorService.doktorGuncelle(id, doktor), HttpStatus.OK);
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> doktorSil(@PathVariable Long id) {
        doktorService.doktorSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tumDoktorlar")
    public ResponseEntity<List<Doktor>> tumDoktorlar() {
        return new ResponseEntity<>(doktorService.tumDoktorlar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doktor> doktorById(@PathVariable Long id) {
        return doktorService.doktorById(id)
                .map(doktor -> new ResponseEntity<>(doktor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/branş/{branş}")
    public ResponseEntity<List<Doktor>> doktorByBrans(@PathVariable String branş) {
        return new ResponseEntity<>(doktorService.doktorByBrans(branş), HttpStatus.OK);
    }
}

