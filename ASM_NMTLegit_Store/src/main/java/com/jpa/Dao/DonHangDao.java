package com.jpa.Dao;

import com.jpa.Entity.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonHangDao extends JpaRepository<DonHangEntity, Integer> {
    List<DonHangEntity> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    
    @Query("SELECT COUNT(dh) FROM DonHangEntity dh WHERE dh.ngayDatHang = CURRENT_DATE")
    long countDonHangHomNay();
    
    

    @Query("SELECT COUNT(dh) FROM DonHangEntity dh WHERE dh.trangThai = 'DaHuy'")
    long countDonHangBiHuy();
    

}
