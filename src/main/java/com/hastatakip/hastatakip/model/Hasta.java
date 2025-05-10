package com.hastatakip.hastatakip.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Hasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String soyad;

    @Column(unique = true, nullable = false) //...
    private String tcKimlikNo;

    private LocalDate dogumTarihi;
    private String cinsiyet;
    private String telefon;
    private String email;
    private String adres;
    private String acilDurumKisi;
}
