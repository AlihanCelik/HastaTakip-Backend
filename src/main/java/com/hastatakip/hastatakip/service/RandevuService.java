package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Doktor;
import com.hastatakip.hastatakip.model.Hasta;
import com.hastatakip.hastatakip.model.Randevu;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RandevuService {

    private final DataSource dataSource;
    private final HastaService hastaService;
    private final DoktorService doktorService;

    public RandevuService(DataSource dataSource, HastaService hastaService, DoktorService doktorService) {
        this.dataSource = dataSource;
        this.hastaService = hastaService;
        this.doktorService = doktorService;
    }

    public Randevu randevuEkle(Randevu randevu) throws SQLException {
        Hasta hasta = hastaService.hastaByTcKimlikNo(randevu.getHasta().getTcKimlikNo());
        if (hasta == null) {
            throw new RuntimeException("Hasta bulunamadı. Lütfen doğru TC Kimlik No girin.");
        }

        // Aynı doktor ve tarihte randevu var mı kontrol et
        if (randevuVarMi(randevu.getDoktor().getId(), randevu.getRandevuTarihi())) {
            throw new RuntimeException("Bu doktorun o tarihte randevusu var. Lütfen başka bir tarih seçin.");
        }

        String sql = "INSERT INTO randevu (doktor_id,hasta_tc_kimlik_no, randevu_tarihi, durum) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, randevu.getDoktor().getId());
            stmt.setString(2, hasta.getTcKimlikNo());
            stmt.setTimestamp(3, Timestamp.valueOf(randevu.getRandevuTarihi())); // LocalDateTime ise
            stmt.setString(4, randevu.getDurum());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Randevu eklenemedi, hiçbir satır etkilenmedi.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    randevu.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Randevu ID'si alınamadı.");
                }
            }
        }

        // Hasta nesnesini güncelle
        randevu.setHasta(hasta);

        return randevu;
    }

    public Randevu randevuGuncelle(Long id, Randevu randevu) throws SQLException {
        // Var olan randevuyu getir
        Randevu mevcut = randevuById(id);
        if (mevcut == null) {
            throw new RuntimeException("Randevu bulunamadı");
        }

        // Hasta değiştiyse hasta bilgisini güncelle
        if (!mevcut.getHasta().getTcKimlikNo().equals(randevu.getHasta().getTcKimlikNo())) {
            Hasta hasta = hastaService.hastaByTcKimlikNo(randevu.getHasta().getTcKimlikNo());
            if (hasta == null) {
                throw new RuntimeException("Hasta bulunamadı. Lütfen doğru TC Kimlik No girin.");
            }
            mevcut.setHasta(hasta);
        }

        // Tarih değiştiyse aynı doktorun o tarihte başka randevusu var mı kontrol et
        if (!mevcut.getRandevuTarihi().equals(randevu.getRandevuTarihi()) &&
                randevuVarMi(randevu.getDoktor().getId(), randevu.getRandevuTarihi())) {
            throw new RuntimeException("Bu doktorun o tarihte randevusu var. Lütfen başka bir tarih seçin.");
        }

        // Güncelleme sorgusu
        String sql = "UPDATE randevu SET doktor_id=?, hasta_id=?, randevu_tarihi=?, durum=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, randevu.getDoktor().getId());
            stmt.setLong(2, mevcut.getHasta().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(randevu.getRandevuTarihi()));
            stmt.setString(4, randevu.getDurum());
            stmt.setLong(5, id);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Randevu güncellenemedi, hiçbir satır etkilenmedi.");
            }
        }

        mevcut.setRandevuTarihi(randevu.getRandevuTarihi());
        mevcut.setDurum(randevu.getDurum());

        return mevcut;
    }

    public void randevuSil(Long id) throws SQLException {
        String sql = "DELETE FROM randevu WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Randevu> tumRandevular() throws SQLException {
        List<Randevu> liste = new ArrayList<>();
        String sql = "SELECT * FROM randevu";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                liste.add(mapRow(rs));
            }
        }
        return liste;
    }

    public List<Randevu> randevularByHastaTc(String tcKimlikNo) throws SQLException {
        List<Randevu> liste = new ArrayList<>();
        String sql =  "SELECT * FROM randevu WHERE hasta_tc_kimlik_no = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tcKimlikNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    liste.add(mapRow(rs));
                }
            }
        }
        return liste;
    }

    public Randevu randevuById(Long id) throws SQLException {
        String sql = "SELECT * FROM randevu WHERE id=?";
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

    // Aynı doktor ve tarihte randevu var mı?
    private boolean randevuVarMi(Long doktorId, java.time.LocalDateTime tarih) throws SQLException {
        String sql = "SELECT COUNT(*) FROM randevu WHERE doktor_id=? AND randevu_tarihi=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, doktorId);
            stmt.setTimestamp(2, Timestamp.valueOf(tarih));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Randevu mapRow(ResultSet rs) throws SQLException {
        Randevu randevu = new Randevu();
        randevu.setId(rs.getLong("id"));

        // Hasta TC Kimlik No'sunu al ve hasta nesnesini getir
        String hastaTc = rs.getString("hasta_tc_kimlik_no");
        Hasta hasta = hastaService.hastaByTcKimlikNo(hastaTc);
        randevu.setHasta(hasta);

        // Doktor ID'yi al ve doktor nesnesini getir
        Long doktorId = rs.getLong("doktor_id");
        Doktor doktor = doktorService.doktorById(doktorId); // DoktorService içinde bu metot olmalı
        randevu.setDoktor(doktor);

        randevu.setRandevuTarihi(rs.getTimestamp("randevu_tarihi").toLocalDateTime());
        randevu.setDurum(rs.getString("durum"));

        return randevu;
    }

}
