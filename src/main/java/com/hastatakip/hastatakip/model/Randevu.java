package com.hastatakip.hastatakip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Randevu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    @ManyToOne
    @JoinColumn(name = "doktor_id")
    private Doktor doktor;

    private LocalDateTime randevuTarihi;
    private String durum; // Planlandı, Tamamlandı, İptal Edildi

    // Getter ve Setter metodları
}

