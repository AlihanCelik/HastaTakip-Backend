package com.hastatakip.hastatakip.controller;

import com.hastatakip.hastatakip.model.TeshisTedavi;
import com.hastatakip.hastatakip.service.TeshisTedaviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teshisTedavi")
public class TeshisTedaviController {

    @Autowired
    private TeshisTedaviService teshisTedaviService;

    @PostMapping("/ekle")
    public ResponseEntity<TeshisTedavi> teshisTedaviEkle(@RequestBody TeshisTedavi teshisTedavi) {
        return new ResponseEntity<>(teshisTedaviService.teshisTedaviEkle(teshisTedavi), HttpStatus.CREATED);
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<TeshisTedavi>> teshisTedaviByHasta(@PathVariable Long hastaId) {
        return new ResponseEntity<>(teshisTedaviService.teshisTedaviByHasta(hastaId), HttpStatus.OK);
    }
}
