package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Doktor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoktorService {

    private final DataSource dataSource;

    public DoktorService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void doktorEkle(Doktor doktor) throws SQLException {
        String sql = "INSERT INTO doktor (ad, soyad, branş, telefon, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doktor.getAd());
            stmt.setString(2, doktor.getSoyad());
            stmt.setString(3, doktor.getBranş());
            stmt.setString(4, doktor.getTelefon());
            stmt.setString(5, doktor.getEmail());
            stmt.executeUpdate();
        }
    }

    public void doktorGuncelle(Long id, Doktor doktor) throws SQLException {
        String sql = "UPDATE doktor SET ad=?, soyad=?, branş=?, telefon=?, email=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doktor.getAd());
            stmt.setString(2, doktor.getSoyad());
            stmt.setString(3, doktor.getBranş());
            stmt.setString(4, doktor.getTelefon());
            stmt.setString(5, doktor.getEmail());
            stmt.setLong(6, id);
            stmt.executeUpdate();
        }
    }

    public void doktorSil(Long id) throws SQLException {
        String sql = "DELETE FROM doktor WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Doktor doktorById(Long id) throws SQLException {
        String sql = "SELECT * FROM doktor WHERE id=?";
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

    public List<Doktor> doktorByBrans(String brans) throws SQLException {
        List<Doktor> doktorList = new ArrayList<>();
        String sql = "SELECT * FROM doktor WHERE branş=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, brans);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doktorList.add(mapRow(rs));
                }
            }
        }
        return doktorList;
    }

    public List<Doktor> tumDoktorlar() throws SQLException {
        List<Doktor> doktorList = new ArrayList<>();
        String sql = "SELECT * FROM doktor";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                doktorList.add(mapRow(rs));
            }
        }
        return doktorList;
    }

    private Doktor mapRow(ResultSet rs) throws SQLException {
        Doktor doktor = new Doktor();
        doktor.setId(rs.getLong("id"));
        doktor.setAd(rs.getString("ad"));
        doktor.setSoyad(rs.getString("soyad"));
        doktor.setBranş(rs.getString("branş"));
        doktor.setTelefon(rs.getString("telefon"));
        doktor.setEmail(rs.getString("email"));
        return doktor;
    }
}
