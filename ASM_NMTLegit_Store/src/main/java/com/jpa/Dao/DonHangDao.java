package com.jpa.Dao;

import com.jpa.Entity.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonHangDao extends JpaRepository<DonHangEntity, Integer> {
    List<DonHangEntity> findByNguoiDung_MaNguoiDung(Integer maNguoiDung);
    
    @Query("SELECT COUNT(dh) FROM DonHangEntity dh WHERE dh.ngayDatHang = CURRENT_DATE")
    long countDonHangHomNay();
    
    

    @Query("SELECT COUNT(dh) FROM DonHangEntity dh WHERE dh.trangThai = 'DaHuy'")
    long countDonHangBiHuy();
    
    
 // Lấy tất cả đơn hàng và sắp xếp theo ngày đặt hàng giảm dần (mới nhất trước)
    List<DonHangEntity> findAllByOrderByNgayDatHangDesc();

    // Lấy đơn hàng theo người dùng và sắp xếp theo ngày giảm dần
    List<DonHangEntity> findByNguoiDung_MaNguoiDungOrderByNgayDatHangDesc(Integer maNguoiDung);

    
    List<DonHangEntity> findByTrangThaiOrderByNgayDatHangDesc(String trangThai);
    
    List<DonHangEntity> findByNgayDatHang(LocalDate ngayDatHang);

    List<DonHangEntity> findByTrangThaiAndNgayDatHang(String trangThai, LocalDate ngayDatHang);
    
    List<DonHangEntity> findByNgayDatHangBetween(LocalDate startDate, LocalDate endDate);
    List<DonHangEntity> findByTrangThaiAndNgayDatHangBetween(String trangThai, LocalDate startDate, LocalDate endDate);

}
