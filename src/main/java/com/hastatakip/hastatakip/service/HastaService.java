package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Hasta;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class HastaService {

    private final DataSource dataSource;

    public HastaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void hastaEkle(Hasta hasta) throws SQLException {
        String sql = "INSERT INTO hasta (ad, soyad, tc_kimlik_no, dogum_tarihi, cinsiyet, telefon, email, adres, acil_durum_kisi) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hasta.getAd());
            stmt.setString(2, hasta.getSoyad());
            stmt.setString(3, hasta.getTcKimlikNo());
            stmt.setDate(4, Date.valueOf(hasta.getDogumTarihi())); // LocalDate ise Date.valueOf kullanılmalı
            stmt.setString(5, hasta.getCinsiyet());
            stmt.setString(6, hasta.getTelefon());
            stmt.setString(7, hasta.getEmail());
            stmt.setString(8, hasta.getAdres());
            stmt.setString(9, hasta.getAcilDurumKisi());
            stmt.executeUpdate();
        }
    }

    public void hastaGuncelle(Long id, Hasta hasta) throws SQLException {
        String sql = "UPDATE hasta SET ad=?, soyad=?, tc_kimlik_no=?, dogum_tarihi=?, cinsiyet=?, telefon=?, email=?, adres=?, acil_durum_kisi=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hasta.getAd());
            stmt.setString(2, hasta.getSoyad());
            stmt.setString(3, hasta.getTcKimlikNo());
            stmt.setDate(4, Date.valueOf(hasta.getDogumTarihi()));
            stmt.setString(5, hasta.getCinsiyet());
            stmt.setString(6, hasta.getTelefon());
            stmt.setString(7, hasta.getEmail());
            stmt.setString(8, hasta.getAdres());
            stmt.setString(9, hasta.getAcilDurumKisi());
            stmt.setLong(10, id);
            stmt.executeUpdate();
        }
    }

    public void hastaSil(Long id) throws SQLException {
        String sql = "DELETE FROM hasta WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Hasta hastaById(Long id) throws SQLException {
        String sql = "SELECT * FROM hasta WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Hasta> hastaByTcKimlikAndAdSoyad(String tcKimlikNo, String ad, String soyad) throws SQLException {
        List<Hasta> hastaList = new ArrayList<>();
        String sql = "SELECT * FROM hasta WHERE tc_kimlik_no=? AND ad=? AND soyad=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tcKimlikNo);
            stmt.setString(2, ad);
            stmt.setString(3, soyad);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    hastaList.add(mapRow(rs));
                }
            }
        }
        return hastaList;
    }

    public List<Hasta> tumHastalar() throws SQLException {
        List<Hasta> hastaList = new ArrayList<>();
        String sql = "SELECT * FROM hasta";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                hastaList.add(mapRow(rs));
            }
        }
        return hastaList;
    }

    private Hasta mapRow(ResultSet rs) throws SQLException {
        Hasta hasta = new Hasta();
        hasta.setId(rs.getLong("id"));
        hasta.setAd(rs.getString("ad"));
        hasta.setSoyad(rs.getString("soyad"));
        hasta.setTcKimlikNo(rs.getString("tc_kimlik_no"));
        hasta.setDogumTarihi(rs.getDate("dogum_tarihi").toLocalDate());
        hasta.setCinsiyet(rs.getString("cinsiyet"));
        hasta.setTelefon(rs.getString("telefon"));
        hasta.setEmail(rs.getString("email"));
        hasta.setAdres(rs.getString("adres"));
        hasta.setAcilDurumKisi(rs.getString("acil_durum_kisi"));
        return hasta;
    }
}
