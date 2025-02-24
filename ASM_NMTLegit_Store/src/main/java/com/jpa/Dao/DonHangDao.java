package com.jpa.Dao;

import com.jpa.Entity.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonHangDao extends JpaRepository<DonHangEntity, Integer> {
    List<DonHangEntity> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
}
