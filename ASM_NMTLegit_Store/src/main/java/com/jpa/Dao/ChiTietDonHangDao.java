package com.jpa.Dao;


import com.jpa.Entity.ChiTietDonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietDonHangDao extends JpaRepository<ChiTietDonHangEntity, Integer> {
    List<ChiTietDonHangEntity> findByDonHang_MaDonHang(Integer maDonHang);
}