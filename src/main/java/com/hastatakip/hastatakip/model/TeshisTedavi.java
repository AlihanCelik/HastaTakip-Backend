package com.hastatakip.hastatakip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TeshisTedavi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    private String teshis;
    private String tedaviYontemi;
    private String kullanilanIlaclar;
    private String tedaviNotlari;

    // Getter ve Setter metodları
}
